package com.example.test_application.dto.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TaskCompleteDTO(
        UUID taskId,
        UUID executorId,
        BigDecimal amount,
        Instant date
) {
}
