package asyncapi.event;

import java.time.Instant;
import java.util.UUID;

public record UserRegisteredEvent(
        UUID userId,
        Instant createdAt) implements KafkaEvent {
}
