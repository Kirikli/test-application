package com.example.search_service.kafka.handler;

import asyncapi.event.KafkaEvent;
import asyncapi.event.TaskAssignExecutorEvent;
import asyncapi.event.TaskCompleteEvent;
import asyncapi.event.TaskCreateEvent;
import com.example.search_service.service.TaskService;
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
    public void handleCreateTask(TaskCreateEvent taskCreateEvent) {
        log.info("Received event task create: {}", taskCreateEvent);
        taskService.createTask(taskCreateEvent);
    }

    @KafkaHandler
    public void handleAssignExecutorTask(TaskAssignExecutorEvent executorEvent) {
        log.info("Received event task assign executor: {}", executorEvent);
        taskService.assignExecutor(executorEvent);

    }

    @KafkaHandler
    public void handleCompleteTask(TaskCompleteEvent taskCompleteEvent) {
        log.info("Received event task complete event: {}", taskCompleteEvent);
        taskService.completeTask(taskCompleteEvent);
    }

    @KafkaHandler(isDefault = true)
    public void ignore(KafkaEvent event) {
        log.info("Ignored event: {}", event);
    }
}