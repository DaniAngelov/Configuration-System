package com.codingtask.configmanagementapi.serializer;

import com.codingtask.configmanagementapi.model.entity.Configuration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class CustomConfigurationSerializer implements Serializer<Configuration> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String s, Configuration configuration) {
        try {
            if (configuration == null) {
                System.out.println("Serializable object is null!");
                return null;
            }
            System.out.println("Serializing...");
            return objectMapper.writeValueAsBytes(configuration);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}


