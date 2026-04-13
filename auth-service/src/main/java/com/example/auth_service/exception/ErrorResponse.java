package com.example.auth_service.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponse(String error, int status, LocalDateTime timestamp) {

    public static ErrorResponse of(String error, HttpStatus status) {
        return new ErrorResponse(error, status.value(), LocalDateTime.now());
    }
}
