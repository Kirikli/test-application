package asyncapi.event;

import asyncapi.enums.TaskStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TaskStreamEvent(UUID taskId,
                              String description,
                              String name,
                              TaskStatus status,
                              UUID executorId,
                              Instant date,
                              BigDecimal amount) implements KafkaEvent {
}
