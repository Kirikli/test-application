package com.example.test_application.kafka.handler;

import asyncapi.event.KafkaEvent;
import asyncapi.event.TaskUpdateEvent;
import com.example.test_application.services.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(
        topics = "${spring.kafka.topics.task}",
        groupId = "${spring.kafka.consumer.group-id}")
public class KafkaTaskEventHandler {

    private final TaskService taskService;

    @KafkaHandler
    public void handleUpdateTask(TaskUpdateEvent taskUpdateEvent) {
        log.info("Received event task update: {}", taskUpdateEvent);
        taskService.updateTask(taskUpdateEvent);
    }

    @KafkaHandler(isDefault = true)
    public void ignore(KafkaEvent event) {
        log.info("Ignored event: {}", event);
    }
}
