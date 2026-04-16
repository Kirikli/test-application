package com.example.test_application.services;

import com.example.test_application.exception.NotFoundException;
import com.example.test_application.model.User;
import com.example.test_application.repositories.UserRepository;
import asyncapi.event.UserStreamEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional
    public void createUser(UserStreamEvent userStreamEvent) {
        User user = new User();
        user.setId(userStreamEvent.id());
        user.setName(userStreamEvent.name());
        user.setEmail(userStreamEvent.email());
        user.setBirthday(userStreamEvent.birthday());
        user.setCreatedAt(userStreamEvent.createdAt());
        userRepository.save(user);
    }
}
