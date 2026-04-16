package com.example.auth_service.service;

import com.example.auth_service.dto.RegistryDTO;
import com.example.auth_service.dto.UserDTO;
import com.example.auth_service.dto.event.UserRegisteredEvent;
import com.example.auth_service.exception.UserAlreadyExistsException;
import com.example.auth_service.mapper.UserMapper;
import com.example.auth_service.model.User;
import com.example.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public UserDTO registryUser(RegistryDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new UserAlreadyExistsException(dto.email());
        }
        User user = new User();
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setBirthday(dto.birthday());
        user.setName(dto.name());
        user.setCreatedAt(Instant.now());
        user = userRepository.save(user);

        eventPublisher.publishEvent(
                new UserRegisteredEvent(
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        user.getBirthday(),
                        user.getCreatedAt()
                )
        );
        return userMapper.toDto(user);
    }
}

