package com.example.auth_service.service;

import asyncapi.event.KafkaEvent;
import com.example.auth_service.dto.event.UserRegisteredEvent;
import asyncapi.event.UserFlowEvent;
import asyncapi.event.UserStreamEvent;
import com.example.auth_service.kafka.KafkaTopicsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, KafkaEvent> kafkaTemplate;
    private final KafkaTopicsProperties topics;

    @EventListener
    public void handleUserRegistered(UserRegisteredEvent event) {
        UserFlowEvent userFlow = new UserFlowEvent(
                event.id(),
                event.createdAt()
        );

        UserStreamEvent userStream = new UserStreamEvent(
                event.id(),
                event.email(),
                event.name(),
                event.birthday(),
                event.createdAt()
        );

        kafkaTemplate.send(topics.userFlow(), String.valueOf(event.id()), userFlow)
                .whenComplete((metadata, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send user flow", ex);
                    } else {
                        log.info("Sent user flow successfully: {}", userFlow);
                    }
                });

        kafkaTemplate.send(topics.userStream(), String.valueOf(event.id()), userStream)
                .whenComplete((metadata, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send create user stream", ex);
                    } else {
                        log.info("Sent create user stream successfully: {}", userStream);
                    }
                });

        log.info("User events sent to Kafka: {}", event.id());
    }
}
