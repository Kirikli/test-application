package com.example.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record RegistryDTO(
        @Email @NotBlank String email,
        @NotBlank String password,
        String name,
        @Past LocalDate birthday) {
}
