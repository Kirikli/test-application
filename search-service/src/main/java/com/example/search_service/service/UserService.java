package com.example.search_service.service;

import asyncapi.event.UserCreateEvent;
import asyncapi.exception.NotFoundException;
import com.example.search_service.model.User;
import com.example.search_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private static final double DEFAULT_THRESHOLD = 0.5;

    @Transactional
    public void createUser(UserCreateEvent createEvent) {
        userRepository.insertIfNotExists(
                createEvent.id(),
                createEvent.email().toLowerCase(),
                createEvent.name(),
                createEvent.birthday());
    }

    @Transactional(readOnly = true)
    public Page<User> fuzzySearchByEmail(String email, Pageable pageable) {
        email = email == null ? "" : email.trim();
        if (email.isEmpty()) {
            return Page.empty(pageable);
        }
        return userRepository.searchByEmailFuzzy(
                email.toLowerCase(),
                DEFAULT_THRESHOLD,
                pageable
        );
    }

    @Transactional(readOnly = true)
    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
