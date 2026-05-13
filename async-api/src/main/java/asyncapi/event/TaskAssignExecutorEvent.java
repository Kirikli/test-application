package asyncapi.event;

import asyncapi.enums.TaskStatus;

import java.util.UUID;

public record TaskAssignExecutorEvent(
        UUID taskId,
        UUID executorId,
        TaskStatus status) implements KafkaEvent {
}
