package asyncapi.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TaskCompleteEvent(
        UUID taskId,
        UUID executorId,
        Instant date,
        BigDecimal amount) implements KafkaEvent {
}

