package com.example.test_application.mappers;

import com.example.test_application.dto.CreateTaskDTO;
import com.example.test_application.dto.TaskDTO;
import com.example.test_application.model.Task;
import com.example.test_application.model.TaskStatus;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toEntity(CreateTaskDTO createTaskDTO) {
        Task task = new Task();
        task.setDescription(createTaskDTO.getDescription());
        task.setName(createTaskDTO.getName());
        task.setStatus(TaskStatus.NEW);
        return task;
    }

    public TaskDTO toDto(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getExecutor(),
                task.getDescription(),
                task.getName(),
                task.getStatus()
        );
    }
}
