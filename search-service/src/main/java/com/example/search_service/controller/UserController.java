package com.example.search_service.controller;

import asyncapi.util.PageResponseBuilder;
import asyncapi.util.PageResponseDTO;
import com.example.search_service.dto.UserDTO;
import com.example.search_service.mapper.UserMapper;
import com.example.search_service.model.User;
import com.example.search_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/search/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/email")
    public PageResponseDTO<UserDTO> searchByEmail(
            @RequestParam String email,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<User> users = userService.fuzzySearchByEmail(email, pageable);

        return PageResponseBuilder.of(
                users.getContent(),
                users.getTotalElements(),
                users.getTotalPages(),
                userMapper::toDto
        );
    }

    @GetMapping
    public UserDTO getUserInfo(@RequestHeader("user-id") UUID userId) {
        User user = userService.getUserById(userId);
        return userMapper.toDto(user);
    }
}
