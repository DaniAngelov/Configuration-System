package com.codingtask.configmanagementapi.serializer;

import com.codingtask.configmanagementapi.model.entity.Configuration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class CustomConfigurationDeserializer implements Deserializer<Configuration> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Configuration deserialize(String s, byte[] bytes) {
        try {
            if (bytes == null) {
                System.out.println("Deserializable object is null!");
                return null;
            }
            System.out.println("Deserializing...");
            return objectMapper.readValue(new String(bytes), Configuration.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
    }
}
