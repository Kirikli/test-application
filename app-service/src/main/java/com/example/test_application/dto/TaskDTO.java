package com.example.test_application.dto;

import com.example.test_application.model.User;
import asyncapi.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TaskDTO {
    private UUID id;
    private User executor;
    private String description;
    private String name;
    private TaskStatus status;
}
