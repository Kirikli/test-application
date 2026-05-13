package com.example.search_service.service;

import asyncapi.event.KafkaEvent;
import asyncapi.event.TaskUpdateEvent;
import com.example.search_service.kafka.KafkaTopicsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, KafkaEvent> kafkaTemplate;
    private final KafkaTopicsProperties topics;

    @EventListener
    public void updateTask(TaskUpdateEvent taskUpdateEvent) {

        kafkaTemplate.send(topics.task(), String.valueOf(taskUpdateEvent.taskId()), taskUpdateEvent)
                .whenComplete((metadata, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send task update event", ex);
                    } else {
                        log.info("Sent task update event successfully: {}", taskUpdateEvent);
                    }
                });
    }
}
