package com.example.test_application.services;

import asyncapi.enums.TaskStatus;
import asyncapi.event.TaskCreateEvent;
import asyncapi.event.TaskUpdateEvent;
import asyncapi.exception.NotFoundException;
import com.example.test_application.dto.UpdateTaskStatusDTO;
import com.example.test_application.dto.event.TaskAssignDTO;
import com.example.test_application.dto.event.TaskCompleteDTO;
import com.example.test_application.dto.event.TaskCreateDTO;
import com.example.test_application.model.Task;
import com.example.test_application.model.User;
import com.example.test_application.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(readOnly = true)
    public Task getTaskById(UUID taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException(
                        "Task with id " + taskId + " not found"
                ));
    }

    @Transactional(readOnly = true)
    public Page<Task> getTaskPage(Pageable pageable) {

        return taskRepository.findAll(pageable);
    }

    @Transactional
    public Task createTask(Task task) {
        task = taskRepository.save(task);

        eventPublisher.publishEvent(
                new TaskCreateDTO(
                        task.getId(),
                        task.getDescription(),
                        task.getName(),
                        task.getStatus(),
                        task.getAmount()
                )
        );

        return task;
    }

    @Transactional
    public Task updateTaskStatus(UUID taskId, UpdateTaskStatusDTO statusDTO) {
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
        task = taskRepository.save(task);
        if (newStatus == TaskStatus.DONE) {
            sendCompleteTask(task);
        }

        return taskRepository.save(task);
    }

    @Transactional
    public Task assignExecutor(UUID taskId, UUID executorId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found"));

        if (task.getExecutor() != null) {
            throw new IllegalStateException("Task already has executor");
        }

        User user = userService.findById(executorId);
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setExecutor(user);
        task = taskRepository.save(task);

        eventPublisher.publishEvent(
                new TaskAssignDTO(
                        task.getId(),
                        user.getId(),
                        task.getStatus()
                )
        );

        return task;
    }

    private void sendCompleteTask(Task task) {
        task = taskRepository.findTaskWithUser(task.getId());
        eventPublisher.publishEvent(
                new TaskCompleteDTO(
                        task.getId(),
                        task.getExecutor().getId(),
                        task.getAmount(),
                        Instant.now()
                )
        );
    }

    @Transactional
    public void updateTask(TaskUpdateEvent taskUpdateEvent) {
        Task task = taskRepository.findById(taskUpdateEvent.taskId())
                .orElseThrow(() -> new NotFoundException("Task not found: " + taskUpdateEvent.taskId()));

        if (taskUpdateEvent.newName() != null) {
            task.setName(taskUpdateEvent.newName());
        }
        if (taskUpdateEvent.newDescription() != null) {
            task.setDescription(taskUpdateEvent.newDescription());
        }

        taskRepository.save(task);
    }
}
