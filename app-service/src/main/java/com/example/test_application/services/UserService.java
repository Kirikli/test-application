package com.example.test_application.services;

import asyncapi.exception.NotFoundException;
import com.example.test_application.model.User;
import com.example.test_application.repositories.UserRepository;
import asyncapi.event.UserCreateEvent;
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
    public void createUser(UserCreateEvent userCreateEvent) {
        User user = new User();
        user.setId(userCreateEvent.id());
        user.setName(userCreateEvent.name());
        user.setEmail(userCreateEvent.email());
        user.setBirthday(userCreateEvent.birthday());
        user.setCreatedAt(userCreateEvent.createdAt());
        userRepository.save(user);
    }
}
