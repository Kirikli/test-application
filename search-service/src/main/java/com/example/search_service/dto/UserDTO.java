package com.example.search_service.dto;

import java.time.LocalDate;
import java.util.UUID;

public record UserDTO(
        UUID id,
        String email,
        String name,
        LocalDate birthday
) {
}
