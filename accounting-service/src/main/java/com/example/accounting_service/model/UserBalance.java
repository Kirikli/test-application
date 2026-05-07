package com.example.accounting_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Table(name = "user_balance")
public class UserBalance {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID userId;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "updated_at")
    private Instant updatedAt;
}
