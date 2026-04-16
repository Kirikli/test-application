package com.example.test_application.kafka;

import asyncapi.event.UserStreamEvent;
import com.example.test_application.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(
        topics = "user-stream-topic",
        groupId = "task-event")
public class KafkaEventHandler {

    private final UserService userService;

    @KafkaHandler
    public void handleCreateTask(UserStreamEvent userStreamEvent) {
        log.info("Received event task create: {}", userStreamEvent);
        userService.createUser(userStreamEvent);
    }
}
