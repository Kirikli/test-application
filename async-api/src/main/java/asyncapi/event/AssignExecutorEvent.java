package asyncapi.event;

import asyncapi.enums.TaskStatus;

import java.util.UUID;

public record AssignExecutorEvent(
        UUID taskId,
        UUID executorId,
        TaskStatus status) implements KafkaEvent {
}
