package com.example.auth_service.controller;

import com.example.auth_service.exception.ErrorResponse;
import com.example.auth_service.exception.InvalidCredentialsException;
import com.example.auth_service.exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handle(UserAlreadyExistsException e) {
        return ErrorResponse.of(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handle(InvalidCredentialsException e) {
        return ErrorResponse.of(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
