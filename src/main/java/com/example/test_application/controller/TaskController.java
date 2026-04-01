package com.example.test_application.controller;

import com.example.test_application.common.PageResponseDTO;
import com.example.test_application.dto.AssignTaskDTO;
import com.example.test_application.dto.CreateTaskDTO;
import com.example.test_application.dto.TaskDTO;
import com.example.test_application.dto.UpdateTaskStatusDTO;
import com.example.test_application.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public PageResponseDTO<TaskDTO> getTasksPage(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "50") int size) {
        return taskService.getTaskPage(page, size);
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
            @RequestBody AssignTaskDTO assignDTO
    ) {
        return taskService.assignExecutor(id, assignDTO.getExecutorId());
    }

    @PatchMapping("/{id}/status")
    public TaskDTO updateStatus(@PathVariable UUID id, @RequestBody UpdateTaskStatusDTO statusDTO) {
        return taskService.updateTaskStatus(id, statusDTO);
    }
}
