package com.example.test_application.controller;

import asyncapi.util.PageResponseBuilder;
import asyncapi.util.PageResponseDTO;
import com.example.test_application.dto.CreateTaskDTO;
import com.example.test_application.dto.TaskDTO;
import com.example.test_application.dto.UpdateTaskStatusDTO;
import com.example.test_application.mappers.TaskMapper;
import com.example.test_application.model.Task;
import com.example.test_application.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping
    public PageResponseDTO<TaskDTO> getTasksPage(
            @PageableDefault(size = 50, sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Task> tasks = taskService.getTaskPage(pageable);
        return PageResponseBuilder.of(
                tasks.getContent(),
                tasks.getTotalElements(),
                tasks.getTotalPages(),
                taskMapper::toDto
        );
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable UUID id) {
        Task task = taskService.getTaskById(id);
        return taskMapper.toDto(task);
    }

    @PostMapping
    public TaskDTO createTask(@RequestBody CreateTaskDTO createTaskDTO) {
        Task task = taskMapper.toEntity(createTaskDTO);
        task = taskService.createTask(task);
        return taskMapper.toDto(task);
    }

    @PatchMapping("/{id}/assign")
    public TaskDTO assignExecutor(
            @PathVariable UUID id,
            @RequestHeader("user-id") UUID userId
    ) {
        Task task = taskService.assignExecutor(id, userId);
        return taskMapper.toDto(task);
    }

    @PatchMapping("/{id}/status")
    public TaskDTO updateStatus(@PathVariable UUID id, @RequestBody UpdateTaskStatusDTO statusDTO) {
        Task task = taskService.updateTaskStatus(id, statusDTO);
        return taskMapper.toDto(task);
    }
}
