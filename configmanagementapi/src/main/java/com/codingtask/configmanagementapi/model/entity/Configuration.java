package com.codingtask.configmanagementapi.model.entity;

import com.codingtask.configmanagementapi.model.enums.EnvironmentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity(name = "configurations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
//@RedisHash(value = "Configuration")
public class Configuration implements Serializable {

    @Schema(hidden = true)
    @Id
//    @Indexed
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
//    @Indexed
    private String name;

    @Column
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Configuration_ConfigurationVersions",
            joinColumns = {@JoinColumn(name = "configuration_id")},
            inverseJoinColumns = {@JoinColumn(name = "configuration_version_id")}
    )
    private List<ConfigurationVersion> configurationVersions;

    @Column
    @Enumerated(EnumType.STRING)
    private EnvironmentType environmentType;
}
