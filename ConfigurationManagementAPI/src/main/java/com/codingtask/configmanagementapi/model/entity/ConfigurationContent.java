package com.codingtask.configmanagementapi.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity(name = "configuration_contents")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
//@RedisHash(value = "Configuration Content")
public class ConfigurationContent implements Serializable {
    @Schema(hidden = true)
    @Id
//    @Indexed
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String propertyName;

    @Column
    private String propertyValue;

    @JsonBackReference
    @ManyToMany(mappedBy = "configurationContents")
    private List<ConfigurationVersion> configurationVersions;
}
