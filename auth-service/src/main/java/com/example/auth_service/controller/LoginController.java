package com.example.auth_service.controller;

import com.example.auth_service.dto.RegistryDTO;
import com.example.auth_service.dto.UserDTO;
import com.example.auth_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @PostMapping("/registration")
    public UserDTO registration(@Valid @RequestBody RegistryDTO dto) {
        return userService.registryUser(dto);
    }
}
