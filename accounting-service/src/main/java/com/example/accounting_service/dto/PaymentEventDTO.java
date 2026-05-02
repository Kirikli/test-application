package com.example.accounting_service.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PaymentEventDTO(
        UUID recordId,
        UUID userId,
        BigDecimal amount,
        Instant date,
        UUID taskId
) {
}
