package com.example.test_application.controller;

import asyncapi.util.PageResponseDTO;
import com.example.test_application.dto.CreateTaskDTO;
import com.example.test_application.dto.TaskDTO;
import com.example.test_application.dto.UpdateTaskStatusDTO;
import com.example.test_application.services.TaskService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping
    public PageResponseDTO<TaskDTO> getTasksPage(
            @PageableDefault(size = 50, sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {
        return taskService.getTaskPage(pageable);
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable UUID id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    public TaskDTO createTask(@RequestBody CreateTaskDTO createTaskDTO) {
        return taskService.createTask(createTaskDTO);
    }

    @PatchMapping("/{id}/assign")
    public TaskDTO assignExecutor(
            @PathVariable UUID id,
            @RequestHeader("user-id") UUID userId
    ) {
        return taskService.assignExecutor(id, userId);
    }

    @PatchMapping("/{id}/status")
    public TaskDTO updateStatus(@PathVariable UUID id, @RequestBody UpdateTaskStatusDTO statusDTO) {
        return taskService.updateTaskStatus(id, statusDTO);
    }
}
