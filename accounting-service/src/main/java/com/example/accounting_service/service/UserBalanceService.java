package com.example.accounting_service.service;

import com.example.accounting_service.dto.UserBalanceDTO;
import com.example.accounting_service.mapper.UserBalanceMapper;
import com.example.accounting_service.model.UserBalance;
import com.example.accounting_service.repository.UserBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import asyncapi.exception.NotFoundException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserBalanceService {

    private final UserBalanceRepository userBalanceRepository;
    private final UserBalanceMapper userBalanceMapper;

    @Transactional
    public void createUserBalance(UUID userId) {
        userBalanceRepository.insertIfNotExists(userId, Instant.now());
    }

    @Transactional(readOnly = true)
    public UserBalanceDTO getUserBalance(UUID userId) {
        UserBalance userBalance = userBalanceRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return userBalanceMapper.toDto(userBalance);
    }

    @Transactional
    public void updateUsersBalance(Map<UUID, BigDecimal> addByUserBalance) {
        Instant now = Instant.now();
        addByUserBalance.forEach((userId, delta) ->
                userBalanceRepository.updateBalance(userId, delta, now)
        );
    }
}
