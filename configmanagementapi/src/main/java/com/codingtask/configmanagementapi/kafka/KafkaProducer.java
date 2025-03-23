package com.codingtask.configmanagementapi.kafka;

import com.codingtask.configmanagementapi.model.entity.Configuration;
import com.codingtask.configmanagementapi.model.enums.EnvironmentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, Configuration> kafkaTemplate;

    public void publishToTopic(Configuration configuration, EnvironmentType environmentType) {
        String topic = "dev";
        if (environmentType.equals(EnvironmentType.PRODUCTION)) {
            topic = "production";
        } else if (environmentType.equals(EnvironmentType.STAGING)) {
            topic = "staging";
        }
        System.out.println("Publishing to topic: { " + topic + " }");
        this.kafkaTemplate.send(topic, configuration);
    }
}
