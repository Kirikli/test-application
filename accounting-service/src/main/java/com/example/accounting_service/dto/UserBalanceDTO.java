package com.example.accounting_service.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record UserBalanceDTO(
        UUID userId,
        BigDecimal amount
) {
}
