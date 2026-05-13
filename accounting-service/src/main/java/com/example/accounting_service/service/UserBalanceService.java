package com.example.accounting_service.service;

import asyncapi.exception.NotFoundException;
import com.example.accounting_service.model.UserBalance;
import com.example.accounting_service.repository.UserBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserBalanceService {

    private final UserBalanceRepository userBalanceRepository;

    @Transactional
    public void createUserBalance(UUID userId) {
        userBalanceRepository.insertIfNotExists(userId, Instant.now());
    }

    @Transactional(readOnly = true)
    public UserBalance getUserBalance(UUID userId) {
        return userBalanceRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional
    public void updateUsersBalance(Map<UUID, BigDecimal> addByUserBalance) {
        Instant now = Instant.now();
        addByUserBalance.forEach((userId, delta) ->
                userBalanceRepository.updateBalance(userId, delta, now)
        );
    }
}
