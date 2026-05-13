package com.example.search_service.model;

import asyncapi.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "task")
public class Task {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id")
    private User executor;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;

    @Column(name = "amount")
    private BigDecimal amount;
}
