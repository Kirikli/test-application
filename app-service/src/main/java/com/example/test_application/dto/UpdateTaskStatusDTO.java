package com.example.test_application.dto;

import com.example.test_application.model.TaskStatus;
import lombok.Data;

@Data
public class UpdateTaskStatusDTO {
    private TaskStatus status;
}
