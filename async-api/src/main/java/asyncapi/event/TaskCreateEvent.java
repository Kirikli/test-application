package asyncapi.event;

import asyncapi.enums.TaskStatus;

import java.util.UUID;

public record TaskCreateEvent(
        UUID id,
        String description,
        String name,
        TaskStatus status) implements KafkaEvent {
}
