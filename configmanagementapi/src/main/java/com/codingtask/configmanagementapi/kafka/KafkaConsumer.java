package com.codingtask.configmanagementapi.kafka;

import com.codingtask.configmanagementapi.model.entity.Configuration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = {"dev", "production", "staging"}, groupId = "mygroup")
    public void consumeFromTopic(Configuration message) throws JsonProcessingException {
        System.out.println("Message consumed: " + mapper.writeValueAsString(message));
    }
}
