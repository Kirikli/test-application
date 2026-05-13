package asyncapi.event;

import asyncapi.enums.TaskStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record TaskCreateEvent(
        UUID id,
        String description,
        String name,
        TaskStatus status,
        BigDecimal amount) implements KafkaEvent {
}
