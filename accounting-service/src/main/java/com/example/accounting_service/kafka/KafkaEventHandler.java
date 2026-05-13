package com.example.accounting_service.kafka;

import asyncapi.event.KafkaEvent;
import asyncapi.event.TaskCompleteEvent;
import asyncapi.event.UserCreateEvent;
import com.example.accounting_service.service.PaymentRecordService;
import com.example.accounting_service.service.UserBalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventHandler {

    private final PaymentRecordService paymentRecordService;
    private final UserBalanceService userBalanceService;

    @KafkaListener(
            topics = "${spring.kafka.topics.task}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void handleCompleteTask(TaskCompleteEvent completeEvent) {
        log.info("Received event payment create: {}", completeEvent);
        paymentRecordService.create(completeEvent);
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.user-stream}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void handleCreateUserBalance(UserCreateEvent userCreateEvent) {
        log.info("Received event user balance create: {}", userCreateEvent);
        userBalanceService.createUserBalance(userCreateEvent.id());
    }

    @KafkaHandler(isDefault = true)
    public void ignore(KafkaEvent event) {
        log.info("Ignored event: {}", event);
    }
}
