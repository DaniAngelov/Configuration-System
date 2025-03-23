package com.codingtask.configmanagementapi.repository;

import com.codingtask.configmanagementapi.model.entity.ConfigurationVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigVersionsRepository extends JpaRepository<ConfigurationVersion, Long> {
}
