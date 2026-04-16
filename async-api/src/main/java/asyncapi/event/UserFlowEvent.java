package asyncapi.event;

import java.time.Instant;
import java.util.UUID;

public record UserFlowEvent(
        UUID userId,
        Instant createdAt) implements KafkaEvent {
}
