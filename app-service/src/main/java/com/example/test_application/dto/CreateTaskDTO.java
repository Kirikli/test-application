package com.example.test_application.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateTaskDTO {
    private String name;
    private String description;
    private BigDecimal amount;
}
