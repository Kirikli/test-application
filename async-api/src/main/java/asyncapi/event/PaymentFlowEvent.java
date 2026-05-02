package asyncapi.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PaymentFlowEvent(
        UUID userId,
        BigDecimal amount,
        Instant date,
        UUID taskId) implements KafkaEvent {
}
