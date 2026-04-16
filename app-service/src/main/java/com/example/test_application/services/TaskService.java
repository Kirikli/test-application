package com.example.test_application.services;

import asyncapi.enums.TaskStatus;
import asyncapi.event.TaskCreateEvent;
import com.example.test_application.common.PageResponseBuilder;
import com.example.test_application.common.PageResponseDTO;
import com.example.test_application.dto.CreateTaskDTO;
import com.example.test_application.dto.TaskDTO;
import com.example.test_application.dto.UpdateTaskStatusDTO;
import com.example.test_application.dto.event.TaskAssignDTO;
import com.example.test_application.exception.NotFoundException;
import com.example.test_application.mappers.TaskMapper;
import com.example.test_application.model.Task;
import com.example.test_application.model.User;
import com.example.test_application.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(readOnly = true)
    public TaskDTO getTaskById(UUID taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException(
                        "Task with id " + taskId + " not found"
                ));
        return taskMapper.toDto(task);
    }

    @Transactional(readOnly = true)
    public PageResponseDTO<TaskDTO> getTaskPage(int page, int size) {
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), size);
        Page<Task> tasks = taskRepository.findAll(pageable);
        return PageResponseBuilder.of(tasks, taskMapper::toDto);
    }

    @Transactional
    public TaskDTO createTask(CreateTaskDTO createTaskDTO) {
        Task task = taskMapper.toEntity(createTaskDTO);
        task = taskRepository.save(task);

        eventPublisher.publishEvent(
                new TaskCreateEvent(
                        task.getId(),
                        task.getDescription(),
                        task.getName(),
                        task.getStatus()
                )
        );

        return taskMapper.toDto(task);
    }

    @Transactional
    public TaskDTO updateTaskStatus(UUID taskId, UpdateTaskStatusDTO statusDTO) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException(
                        "Task with id " + taskId + " not found"
                ));

        TaskStatus currentStatus = task.getStatus();
        TaskStatus newStatus = statusDTO.getStatus();

        if (!currentStatus.canUpdateStatus(newStatus)) {
            throw new IllegalStateException(
                    "Cannot change status from " + currentStatus + " to " + newStatus
            );
        }

        task.setStatus(newStatus);

        return taskMapper.toDto(taskRepository.save(task));
    }

    @Transactional
    public TaskDTO assignExecutor(UUID taskId, UUID executorId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found"));

        if (task.getExecutor() != null) {
            throw new IllegalStateException("Task already has executor");
        }

        User user = userService.findById(executorId);
        task.setExecutor(user);
        task = taskRepository.save(task);

        eventPublisher.publishEvent(
                new TaskAssignDTO(
                        task.getId(),
                        user.getId(),
                        task.getStatus()
                )
        );

        return taskMapper.toDto(task);
    }
}
