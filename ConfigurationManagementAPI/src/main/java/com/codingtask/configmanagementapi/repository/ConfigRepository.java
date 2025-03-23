package com.codingtask.configmanagementapi.repository;

import com.codingtask.configmanagementapi.model.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("jpaConfigRepository")
public interface ConfigRepository extends JpaRepository<Configuration, Long> {
    Configuration findByName(String name);
}
