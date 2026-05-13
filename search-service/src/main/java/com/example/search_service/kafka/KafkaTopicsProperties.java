package com.example.search_service.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.kafka.topics")
public record KafkaTopicsProperties(
        String user,
        String task) {
}
