package com.example.test_application.dto.event;

import asyncapi.enums.TaskStatus;

import java.util.UUID;

public record TaskCreateDTO(
        UUID id,
        String description,
        String name,
        TaskStatus status) {
}
