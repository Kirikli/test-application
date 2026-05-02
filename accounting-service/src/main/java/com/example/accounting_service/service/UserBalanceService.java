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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserBalanceService {

    private final UserBalanceRepository userBalanceRepository;
    private final UserBalanceMapper userBalanceMapper;

    @Transactional
    public void createUserBalance(UUID userId) {
        UserBalance userBalance = new UserBalance();
        userBalance.setUserId(userId);
        userBalance.setBalance(new BigDecimal(0));
        userBalanceRepository.save(userBalance);
    }

    @Transactional
    public void updateAmount(UUID userId, BigDecimal payment) {
        UserBalance userBalance = userBalanceRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        userBalance.setBalance(userBalance.getBalance().add(payment));
        userBalance.setUpdatedAt(Instant.now());
        userBalanceRepository.save(userBalance);
    }

    @Transactional(readOnly = true)
    public UserBalanceDTO getUserBalance(UUID userId) {
        UserBalance userBalance = userBalanceRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return userBalanceMapper.toDto(userBalance);
    }
}
