package com.codingtask.configmanagementapi.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableRedisRepositories(repositoryImplementationPostfix = "redis")
@EnableJpaRepositories(repositoryImplementationPostfix = "jpa")
public class JpaRedisConfiguration {
}
