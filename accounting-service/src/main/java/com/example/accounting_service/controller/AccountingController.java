package com.example.accounting_service.controller;

import asyncapi.util.PageResponseBuilder;
import asyncapi.util.PageResponseDTO;
import com.example.accounting_service.dto.PaymentRecordDTO;
import com.example.accounting_service.dto.UserBalanceDTO;
import com.example.accounting_service.mapper.PaymentRecordMapper;
import com.example.accounting_service.mapper.UserBalanceMapper;
import com.example.accounting_service.model.PaymentRecord;
import com.example.accounting_service.model.UserBalance;
import com.example.accounting_service.service.PaymentRecordService;
import com.example.accounting_service.service.UserBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounting")
public class AccountingController {

    private final PaymentRecordService paymentRecordService;
    private final UserBalanceService userBalanceService;
    private final PaymentRecordMapper paymentRecordMapper;
    private final UserBalanceMapper userBalanceMapper;

    @GetMapping("/balance")
    public UserBalanceDTO getBalance(@RequestHeader("user-id") UUID userId) {
        UserBalance userBalance = userBalanceService.getUserBalance(userId);
        return userBalanceMapper.toDto(userBalance);
    }

    @GetMapping("/payments")
    public PageResponseDTO<PaymentRecordDTO> getPayments(
            @RequestHeader("user-id") UUID userId,
            @PageableDefault(size = 50, sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PaymentRecord> payments = paymentRecordService.getUserPayments(userId, pageable);
        return PageResponseBuilder.of(
                payments.getContent(),
                payments.getTotalElements(),
                payments.getTotalPages(),
                paymentRecordMapper::toDto
        );
    }
}
