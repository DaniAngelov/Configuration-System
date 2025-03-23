package com.codingtask.configmanagementapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class GetConfigurationDTO {
    @Schema(name = "name", example = "config1.properties", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    private List<ConfigurationVersionDTO> configurationVersionDTOS;
    @Schema(name = "environmentType", example = "dev", requiredMode = Schema.RequiredMode.REQUIRED)
    private String environmentType;
}
