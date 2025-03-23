package com.codingtask.configmanagementapi.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity(name = "configuration_versions")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
//@RedisHash(value = "Configuration Version")
public class ConfigurationVersion implements Serializable {
    @Schema(hidden = true)
    @Id
//    @Indexed
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private long version;

    @JsonBackReference
    @ManyToMany(mappedBy = "configurationVersions")
    private List<Configuration> configurations;

    @Column
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "ConfigurationVersion_ConfigurationContents",
            joinColumns = {@JoinColumn(name = "configuration_version_id")},
            inverseJoinColumns = {@JoinColumn(name = "configuration_content_id")}
    )
    private List<ConfigurationContent> configurationContents;
}
