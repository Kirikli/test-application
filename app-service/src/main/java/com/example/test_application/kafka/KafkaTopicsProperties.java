package com.example.test_application.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.kafka.topics")
public record KafkaTopicsProperties(
        String assignExecutor,
        String createTask) {
}
