package com.example.accounting_service.dto;

import asyncapi.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PaymentRecordDTO(
        UUID id,
        UUID userId,
        UUID taskId,
        BigDecimal amount,
        Instant date,
        PaymentStatus status
) {
}
