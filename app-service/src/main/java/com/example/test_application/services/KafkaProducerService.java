package com.example.test_application.services;

import asyncapi.event.TaskAssignExecutorEvent;
import asyncapi.event.KafkaEvent;
import asyncapi.event.TaskCompleteEvent;
import asyncapi.event.TaskCreateEvent;
import com.example.test_application.dto.event.TaskAssignDTO;
import com.example.test_application.dto.event.TaskCompleteDTO;
import com.example.test_application.dto.event.TaskCreateDTO;
import com.example.test_application.kafka.KafkaTopicsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, KafkaEvent> kafkaTemplate;
    private final KafkaTopicsProperties topics;

    @EventListener
    public void handleCreateTask(TaskCreateDTO taskCreateDTO) {
        TaskCreateEvent event = new TaskCreateEvent(
                taskCreateDTO.id(),
                taskCreateDTO.description(),
                taskCreateDTO.name(),
                taskCreateDTO.status(),
                taskCreateDTO.amount());

        kafkaTemplate.send(topics.task(), String.valueOf(event.id()), event)
                .whenComplete((metadata, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send task create event", ex);
                    } else {
                        log.info("Sent task create event successfully: {}", event);
                    }
                });
    }

    @EventListener
    public void handleTaskAssignExecutor(TaskAssignDTO assignDTO) {

        TaskAssignExecutorEvent event = new TaskAssignExecutorEvent(
                assignDTO.taskId(),
                assignDTO.executorId(),
                assignDTO.status()
        );

        kafkaTemplate.send(topics.task(), String.valueOf(event.taskId()), event)
                .whenComplete((metadata, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send assign execute event", ex);
                    } else {
                        log.info("Sent assign execute event successfully: {}", event);
                    }
                });
    }

    @EventListener
    public void handleCompleteTask(TaskCompleteDTO taskCompleteDTO) {
        TaskCompleteEvent event = new TaskCompleteEvent(
                taskCompleteDTO.taskId(),
                taskCompleteDTO.executorId(),
                taskCompleteDTO.date(),
                taskCompleteDTO.amount()
        );

        kafkaTemplate.send(topics.task(), String.valueOf(event.taskId()), event)
                .whenComplete((metadata, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send assign complete task event", ex);
                    } else {
                        log.info("Sent complete task successfully: {}", event);
                    }
                });
    }
}
