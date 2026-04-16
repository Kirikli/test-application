package asyncapi.event;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;


public record UserStreamEvent(
        UUID id,
        String email,
        String name,
        LocalDate birthday,
        Instant createdAt) implements KafkaEvent {
}
