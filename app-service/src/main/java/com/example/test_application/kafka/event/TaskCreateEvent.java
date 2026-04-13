package com.example.test_application.kafka.event;

import com.example.test_application.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreateEvent implements KafkaEvent {
    private UUID id;
    private String description;
    private String name;
    private TaskStatus status;
}
