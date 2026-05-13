package com.example.auth_service.dto.event;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record UserRegisteredDTO(
        UUID id,
        String email,
        String name,
        LocalDate birthday,
        Instant createdAt
) {
}
