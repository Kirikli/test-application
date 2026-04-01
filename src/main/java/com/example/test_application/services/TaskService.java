package com.example.test_application.services;

import com.example.test_application.common.PageResponseBuilder;
import com.example.test_application.common.PageResponseDTO;
import com.example.test_application.dto.CreateTaskDTO;
import com.example.test_application.dto.TaskDTO;
import com.example.test_application.dto.UpdateTaskStatusDTO;
import com.example.test_application.exception.NotFoundException;
import com.example.test_application.kafka.event.AssignExecutorEvent;
import com.example.test_application.kafka.event.KafkaEvent;
import com.example.test_application.kafka.event.TaskCreateEvent;
import com.example.test_application.mappers.TaskMapper;
import com.example.test_application.model.Task;
import com.example.test_application.model.TaskStatus;
import com.example.test_application.model.User;
import com.example.test_application.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final KafkaTemplate<String, KafkaEvent> kafkaTemplate;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserService userService;

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
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> tasks = taskRepository.findAll(pageable);
        return PageResponseBuilder.of(tasks, taskMapper::toDto);
    }

    public TaskDTO createTaskSendKafka(CreateTaskDTO createTaskDTO) {
        Task task = createTask(createTaskDTO);

        TaskCreateEvent event = new TaskCreateEvent(
                task.getId(),
                task.getDescription(),
                task.getName(),
                task.getStatus());

        kafkaTemplate.send("task-created-event-topic", String.valueOf(task.getId()), event)
                .whenComplete((metadata, ex) -> {
                    if (ex != null) {
                        LOGGER.error("Failed to send task create event", ex);
                    } else {
                        LOGGER.info("Sent task create event successfully: {}", event);
                    }
                });

        return taskMapper.toDto(task);
    }

    @Transactional
    public Task createTask(CreateTaskDTO createTaskDTO) {
        Task task = taskMapper.toEntity(createTaskDTO);
        task = taskRepository.save(task);
        return task;
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

    public TaskDTO assignExecutorSendKafka(UUID taskId, UUID executorId) {
        Task task = assignExecutor(taskId, executorId);

        AssignExecutorEvent event = new AssignExecutorEvent(
                task.getId(),
                task.getExecutor().getId(),
                task.getStatus()
        );

        kafkaTemplate.send("assign-executor-event-topic", String.valueOf(task.getId()), event)
                .whenComplete((metadata, ex) -> {
                    if (ex != null) {
                        LOGGER.error("Failed to send assign execute event", ex);
                    } else {
                        LOGGER.info("Sent assign execute event successfully: {}", event);
                    }
                });

        return taskMapper.toDto(task);
    }

    @Transactional
    public Task assignExecutor(UUID taskId, UUID executorId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found"));

        if (task.getExecutor() != null) {
            throw new IllegalStateException("Task already has executor");
        }

        User user = userService.findById(executorId);
        task.setExecutor(user);

        return taskRepository.save(task);
    }
}
