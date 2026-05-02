package com.example.test_application.mappers;

import asyncapi.enums.TaskStatus;
import com.example.test_application.dto.CreateTaskDTO;
import com.example.test_application.dto.TaskDTO;
import com.example.test_application.model.Task;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TaskMapper {

    public Task toEntity(CreateTaskDTO createTaskDTO) {
        Task task = new Task();
        task.setDescription(createTaskDTO.getDescription());
        task.setName(createTaskDTO.getName());
        task.setStatus(TaskStatus.NEW);
        task.setAmount(createTaskDTO.getAmount() != null ? createTaskDTO.getAmount() : BigDecimal.ZERO);
        return task;
    }

    public TaskDTO toDto(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getExecutor(),
                task.getDescription(),
                task.getName(),
                task.getStatus(),
                task.getAmount()
        );
    }
}
