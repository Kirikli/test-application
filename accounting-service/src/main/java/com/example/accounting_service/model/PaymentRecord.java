package com.example.accounting_service.model;

import asyncapi.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "payment_record")
public class PaymentRecord {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "task_id")
    private UUID taskId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "date")
    private Instant date;

    @Column(name = "status")
    private PaymentStatus status;
}
