package com.example.accounting_service.controller;

import asyncapi.util.PageResponseDTO;
import com.example.accounting_service.dto.PaymentRecordDTO;
import com.example.accounting_service.dto.UserBalanceDTO;
import com.example.accounting_service.service.PaymentRecordService;
import com.example.accounting_service.service.UserBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounting")
public class AccountingController {

    private final PaymentRecordService paymentRecordService;
    private final UserBalanceService userBalanceService;

    @GetMapping("/balance")
    public UserBalanceDTO getBalance(@RequestHeader("user-id") UUID userId) {
        return userBalanceService.getUserBalance(userId);
    }

    @GetMapping("/payments")
    public PageResponseDTO<PaymentRecordDTO> getPayments(
            @RequestHeader("user-id") UUID userId,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "50") int size) {
        return paymentRecordService.getUserPayments(userId, page, size);
    }
}
