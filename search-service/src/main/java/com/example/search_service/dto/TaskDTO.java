package com.example.search_service.dto;

import asyncapi.enums.TaskStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record TaskDTO(
        UUID id,
        UUID executorId,
        String description,
        String name,
        TaskStatus status,
        BigDecimal amount
) {
}
