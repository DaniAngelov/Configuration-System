package com.codingtask.configmanagementapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ConfigurationContentDTO {
    @Schema(name = "propertyName", example = "propertyName1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String propertyName;
    @Schema(name = "propertyValue", example = "propertyValue1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String propertyValue;
}
