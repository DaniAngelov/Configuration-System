package com.codingtask.configmanagementapi.repository;

import com.codingtask.configmanagementapi.model.entity.ConfigurationContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigContentsRepository extends JpaRepository<ConfigurationContent, Long> {
    ConfigurationContent findByPropertyName(String propertyName);
}
