package com.example.search_service.controller;

import asyncapi.enums.TaskStatus;
import asyncapi.util.PageResponseBuilder;
import asyncapi.util.PageResponseDTO;
import com.example.search_service.dto.TaskDTO;
import com.example.search_service.dto.TaskUpdateDTO;
import com.example.search_service.mapper.TaskMapper;
import com.example.search_service.model.Task;
import com.example.search_service.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/search/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PatchMapping("/test/{id}")
    public TaskDTO updateTask(
            @PathVariable UUID id,
            @RequestBody TaskUpdateDTO taskUpdateDTO) {
        Task task = taskService.updateTask(id, taskUpdateDTO.name(), taskUpdateDTO.description());
        return taskMapper.toDTO(task);
    }

    @GetMapping("/name")
    public PageResponseDTO<TaskDTO> searchByName(
            @RequestParam String name,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<Task> tasks = taskService.searchTasksByName(name, pageable);
        return PageResponseBuilder.of(
                tasks.getContent(),
                tasks.getTotalElements(),
                tasks.getTotalPages(),
                taskMapper::toDTO
        );
    }

    @GetMapping("/status")
    public PageResponseDTO<TaskDTO> filterByStatus(
            @RequestParam TaskStatus status,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<Task> tasks = taskService.findTasksByStatus(status, pageable);
        return PageResponseBuilder.of(
                tasks.getContent(),
                tasks.getTotalElements(),
                tasks.getTotalPages(),
                taskMapper::toDTO
        );
    }
}
