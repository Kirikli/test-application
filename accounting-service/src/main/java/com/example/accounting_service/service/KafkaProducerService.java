package com.example.accounting_service.service;

import asyncapi.event.KafkaEvent;
import asyncapi.event.PaymentFlowEvent;
import com.example.accounting_service.dto.PaymentEventDTO;
import com.example.accounting_service.kafka.KafkaTopicsProperties;
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
    public void handleCreateTask(PaymentEventDTO paymentEventDTO) {
        PaymentFlowEvent paymentEvent = new PaymentFlowEvent(
                paymentEventDTO.userId(),
                paymentEventDTO.amount(),
                paymentEventDTO.date(),
                paymentEventDTO.taskId());

        kafkaTemplate.send(topics.payment(), String.valueOf(paymentEventDTO.recordId()), paymentEvent)
                .whenComplete((metadata, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send payment event", ex);
                    } else {
                        log.info("Sent payment event successfully: {}", paymentEvent);
                    }
                });
    }
}
