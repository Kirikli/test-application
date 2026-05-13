package com.example.search_service.kafka.handler;

import asyncapi.event.KafkaEvent;
import asyncapi.event.UserCreateEvent;
import com.example.search_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(
        topics = "${spring.kafka.topics.user}",
        groupId = "${spring.kafka.consumer.group-id}")
public class KafkaUserEventHandler {

    private final UserService userService;

    @KafkaHandler
    public void handleCreateUser(UserCreateEvent userCreateEvent) {
        log.info("Received event user create: {}", userCreateEvent);
        userService.createUser(userCreateEvent);
    }

    @KafkaHandler(isDefault = true)
    public void ignore(KafkaEvent event) {
        log.info("Ignored event: {}", event);
    }
}
