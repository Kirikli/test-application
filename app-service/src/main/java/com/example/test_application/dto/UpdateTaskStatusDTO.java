package com.example.test_application.dto;

import asyncapi.enums.TaskStatus;
import lombok.Data;

@Data
public class UpdateTaskStatusDTO {
    private TaskStatus status;
}
