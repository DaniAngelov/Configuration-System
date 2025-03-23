package com.codingtask.configmanagementapi.service.impl;

import com.codingtask.configmanagementapi.exception.CustomConfigurationException;
import com.codingtask.configmanagementapi.model.dto.*;
import com.codingtask.configmanagementapi.model.entity.Configuration;
import com.codingtask.configmanagementapi.model.entity.ConfigurationContent;
import com.codingtask.configmanagementapi.model.entity.ConfigurationVersion;
import com.codingtask.configmanagementapi.model.enums.EnvironmentType;
import com.codingtask.configmanagementapi.repository.ConfigContentsRepository;
import com.codingtask.configmanagementapi.repository.ConfigRepository;
import com.codingtask.configmanagementapi.repository.ConfigVersionsRepository;
import com.codingtask.configmanagementapi.service.ConfigService;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ConfigServiceImplementation implements ConfigService {

    private ConfigRepository configRepository;

    private ConfigContentsRepository configContentsRepository;

    private ConfigVersionsRepository configVersionsRepository;

//    private RedisConfigRepository redisConfigRepository;

    @Synchronized
    @Override
    public Configuration addConfiguration(ConfigurationDTO configurationDTO) throws CustomConfigurationException {
        Configuration foundConfiguration = configRepository.findByName(configurationDTO.getName());
        if (foundConfiguration != null) {
            throw new CustomConfigurationException("There is already such configuration!");
        }
        Configuration configuration = Configuration.builder()
                .name(configurationDTO.getName())
                .environmentType(EnvironmentType.valueOf(configurationDTO.getEnvironmentType().toUpperCase()))
                .configurationVersions(getConfigurationVersionDTOs(configurationDTO.getConfigurationContents()))
                .build();

        return configRepository.save(configuration);
    }

    @Override
    public List<GetConfigurationDTO> getConfigurations() {
        List<Configuration> configurations = configRepository.findAll();
        return convertToConfigurationDTO(configurations);
    }

    @Synchronized
    @Override
    public void deleteConfiguration(DeleteConfigurationDTO deleteConfigurationDTO) {
        Configuration configuration = configRepository.findByName(deleteConfigurationDTO.getName());
        if (configuration != null) {
            configRepository.delete(configuration);
        }
    }

    @Synchronized
    @Override
    public Configuration updateConfiguration(UpdateConfigurationDTO updateConfigurationDTO) throws CustomConfigurationException {
        Configuration configuration = configRepository.findByName(updateConfigurationDTO.getName());
        if (configuration == null) {
            throw new CustomConfigurationException("There is no such configuration!");
        }
        getNewVersionWithAddedContents(configuration.getConfigurationVersions(), updateConfigurationDTO.getConfigurationContents());
        if (!configuration.getEnvironmentType().toString().equalsIgnoreCase(updateConfigurationDTO.getEnvironmentType())) {
            configuration.setEnvironmentType(EnvironmentType.valueOf(updateConfigurationDTO.getEnvironmentType().toUpperCase()));
        }
        return configRepository.save(configuration);
    }

    private void getNewVersionWithAddedContents(List<ConfigurationVersion> configurationVersionOriginal, List<ConfigurationContentDTO> configurationContentDTOS) {
        ConfigurationVersion currentConfigurationVersion = configurationVersionOriginal.getLast();
        ConfigurationVersion newConfigurationVersion = new ConfigurationVersion();
        newConfigurationVersion.setVersion(currentConfigurationVersion.getVersion());
        newConfigurationVersion.setConfigurationContents(new ArrayList<>(currentConfigurationVersion.getConfigurationContents()));
        boolean shouldVersionChange = false;
        for (ConfigurationContentDTO configurationContentDTO : configurationContentDTOS) {
            ConfigurationContent configurationContent = findConfigurationContent(newConfigurationVersion.getConfigurationContents(), configurationContentDTO.getPropertyName());
            if (configurationContent == null) {
                shouldVersionChange = true;
                newConfigurationVersion.getConfigurationContents().add(createConfigurationContent(configurationContentDTO));
            } else {
                if (!configurationContent.getPropertyValue().equalsIgnoreCase(configurationContentDTO.getPropertyValue())) {
                    shouldVersionChange = true;
                    ConfigurationContent newConfigurationContent = createCopyConfigurationContent(configurationContent, configurationContentDTO.getPropertyValue());
                    newConfigurationVersion.getConfigurationContents().remove(configurationContent);
                    newConfigurationVersion.getConfigurationContents().add(newConfigurationContent);
                    configContentsRepository.save(newConfigurationContent);
                }
            }
        }
        if (shouldVersionChange) {
            newConfigurationVersion.setVersion(currentConfigurationVersion.getVersion() + 1);
            configurationVersionOriginal.add(configVersionsRepository.save(newConfigurationVersion));
        }
    }

    private ConfigurationContent createCopyConfigurationContent(ConfigurationContent configurationContent, String newValue) {
        return ConfigurationContent.builder()
                .propertyName(configurationContent.getPropertyName())
                .propertyValue(newValue)
                .build();
    }

    private ConfigurationContent findConfigurationContent(List<ConfigurationContent> contents, String propertyName) {
        for (ConfigurationContent configurationContent : contents) {
            if (configurationContent.getPropertyName().equalsIgnoreCase(propertyName)) {
                return configurationContent;
            }
        }
        return null;
    }

    private List<ConfigurationVersion> getConfigurationVersionDTOs(List<ConfigurationContentDTO> configurationContents) {
        List<ConfigurationVersion> configurationVersionList = new ArrayList<>();
        ConfigurationVersion configurationVersion =
                ConfigurationVersion.builder()
                        .version(1L)
                        .configurationContents(createConfigurationContents(configurationContents))
                        .build();
        configurationVersionList.add(configVersionsRepository.save(configurationVersion));
        return configurationVersionList;
    }

    private List<ConfigurationContent> createConfigurationContents(List<ConfigurationContentDTO> configurationContentDTOS) {
        List<ConfigurationContent> configurationContentList = new ArrayList<>();
        for (ConfigurationContentDTO configurationContentDTO : configurationContentDTOS) {
            ConfigurationContent configurationContent = ConfigurationContent.builder()
                    .propertyName(configurationContentDTO.getPropertyName())
                    .propertyValue(configurationContentDTO.getPropertyValue())
                    .build();
            configContentsRepository.save(configurationContent);
            configurationContentList.add(configurationContent);
        }
        return configurationContentList;
    }

    private ConfigurationContent createConfigurationContent(ConfigurationContentDTO configurationContentDTO) {
        return configContentsRepository.save(ConfigurationContent.builder()
                .propertyName(configurationContentDTO.getPropertyName())
                .propertyValue(configurationContentDTO.getPropertyValue())
                .build());
    }

    private List<GetConfigurationDTO> convertToConfigurationDTO(List<Configuration> configurations) {
        List<GetConfigurationDTO> configurationDTOS = new ArrayList<>();
        for (Configuration configuration : configurations) {
            GetConfigurationDTO configurationDTO = GetConfigurationDTO.builder()
                    .environmentType(configuration.getEnvironmentType().toString())
                    .name(configuration.getName())
                    .configurationVersionDTOS(getConfigurationVersionDTOS(configuration.getConfigurationVersions()))
                    .build();
            configurationDTOS.add(configurationDTO);
        }
        return configurationDTOS;
    }

    private List<ConfigurationVersionDTO> getConfigurationVersionDTOS(List<ConfigurationVersion> configurationVersions) {
        List<ConfigurationVersionDTO> configurationVersionDTOS = new ArrayList<>();
        for (ConfigurationVersion configurationVersion : configurationVersions) {
            ConfigurationVersionDTO configurationVersionDTO = ConfigurationVersionDTO.builder()
                    .version(configurationVersion.getVersion())
                    .configurationContents(getConfigurationContentDTOS(configurationVersion.getConfigurationContents()))
                    .build();
            configurationVersionDTOS.add(configurationVersionDTO);
        }
        return configurationVersionDTOS;
    }

    private List<ConfigurationContentDTO> getConfigurationContentDTOS(List<ConfigurationContent> configurationContents) {
        List<ConfigurationContentDTO> configurationContentDTOS = new ArrayList<>();
        for (ConfigurationContent configurationContent : configurationContents) {
            ConfigurationContentDTO configurationDTO = ConfigurationContentDTO.builder()
                    .propertyName(configurationContent.getPropertyName())
                    .propertyValue(configurationContent.getPropertyValue())
                    .build();
            configurationContentDTOS.add(configurationDTO);
        }
        return configurationContentDTOS;
    }
}
