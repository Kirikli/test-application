package com.example.search_service.service;

import asyncapi.enums.TaskStatus;
import asyncapi.event.TaskAssignExecutorEvent;
import asyncapi.event.TaskCompleteEvent;
import asyncapi.event.TaskCreateEvent;
import asyncapi.event.TaskUpdateEvent;
import asyncapi.exception.NotFoundException;
import com.example.search_service.model.Task;
import com.example.search_service.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void createTask(TaskCreateEvent event) {
        if (taskRepository.existsById(event.id())) {
            return;
        }

        Task task = new Task();
        task.setId(event.id());
        task.setName(event.name());
        task.setDescription(event.description());
        task.setStatus(event.status());
        task.setAmount(event.amount());

        taskRepository.save(task);
    }

    @Transactional
    public void assignExecutor(TaskAssignExecutorEvent executorEvent) {
        taskRepository.assignExecutor(executorEvent.taskId(), executorEvent.executorId());
    }

    @Transactional
    public void completeTask(TaskCompleteEvent taskCompleteEvent) {
        taskRepository.completeTask(taskCompleteEvent.taskId(), TaskStatus.DONE);
    }

    @Transactional
    public Task updateTask(UUID taskId, String newName, String newDescription) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found: " + taskId));

        if (newName != null) {
            task.setName(newName);
        }
        if (newDescription != null) {
            task.setDescription(newDescription);
        }
        task = taskRepository.save(task);

        eventPublisher.publishEvent(
                new TaskUpdateEvent(
                        task.getId(),
                        task.getName(),
                        task.getDescription()
                )
        );
        return task;
    }

    public Page<Task> searchTasksByName(String name, Pageable pageable) {
        name = name == null ? "" : name.trim();
        if (name.isEmpty()) {
            return Page.empty(pageable);
        }
        return taskRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    public Page<Task> findTasksByStatus(TaskStatus status, Pageable pageable) {
        return taskRepository.findByStatus(status, pageable);
    }
}
