package com.example.accounting_service.sheduler;

import com.example.accounting_service.model.PaymentRecord;
import com.example.accounting_service.service.PaymentRecordService;
import com.example.accounting_service.service.UserBalanceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentScheduler {

    private final PaymentRecordService paymentRecordService;
    private final UserBalanceService userBalanceService;

    @Scheduled(cron = "0 0 0 */14 * *")
    //@Scheduled(cron = "0 */2 * * * *")
    public void processPayments() {
        int batchSize = 100;
        while (true) {
            List<PaymentRecord> pending = paymentRecordService.findPendingBatch(batchSize);
            if (pending.isEmpty()) {
                return;
            }
            processBatch(pending);
        }
    }

    @Transactional
    public void processBatch(List<PaymentRecord> pending) {
        Map<UUID, BigDecimal> deltasByUser = pending.stream()
                .collect(Collectors.groupingBy(
                        PaymentRecord::getUserId,
                        Collectors.mapping(
                                PaymentRecord::getAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));
        userBalanceService.updateUsersBalance(deltasByUser);
        paymentRecordService.updatePaymentRecord(pending);
    }
}
