package asyncapi.event;

import java.util.UUID;

public record TaskUpdateEvent(
        UUID taskId,
        String newName,
        String newDescription) implements KafkaEvent {
}
