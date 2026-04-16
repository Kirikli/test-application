package com.example.test_application.dto.event;

import asyncapi.enums.TaskStatus;

import java.util.UUID;

public record TaskAssignDTO(
        UUID taskId,
        UUID executorId,
        TaskStatus status) {
}
