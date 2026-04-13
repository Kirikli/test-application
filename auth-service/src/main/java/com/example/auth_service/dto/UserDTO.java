package com.example.auth_service.dto;

import java.util.UUID;

public record UserDTO(UUID id, String email) {
}
