package com.codingtask.configmanagementapi.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ConfigurationVersionDTO {
    private long version;
    private List<ConfigurationContentDTO> configurationContents;
}
