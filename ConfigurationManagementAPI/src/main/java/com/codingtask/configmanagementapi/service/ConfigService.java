package com.codingtask.configmanagementapi.service;

import com.codingtask.configmanagementapi.exception.CustomConfigurationException;
import com.codingtask.configmanagementapi.model.dto.ConfigurationDTO;
import com.codingtask.configmanagementapi.model.dto.DeleteConfigurationDTO;
import com.codingtask.configmanagementapi.model.dto.GetConfigurationDTO;
import com.codingtask.configmanagementapi.model.dto.UpdateConfigurationDTO;
import com.codingtask.configmanagementapi.model.entity.Configuration;

import java.util.List;

public interface ConfigService {
    Configuration addConfiguration(ConfigurationDTO configurationDTO) throws CustomConfigurationException;

    List<GetConfigurationDTO> getConfigurations();

    void deleteConfiguration(DeleteConfigurationDTO deleteConfigurationDTO);

    Configuration updateConfiguration(UpdateConfigurationDTO updateConfigurationDTO) throws CustomConfigurationException;
}
