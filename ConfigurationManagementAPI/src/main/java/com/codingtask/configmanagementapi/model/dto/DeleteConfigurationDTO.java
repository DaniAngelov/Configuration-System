package com.codingtask.configmanagementapi.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DeleteConfigurationDTO {
    @Schema(name = "name", example = "config1.properties", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
}
