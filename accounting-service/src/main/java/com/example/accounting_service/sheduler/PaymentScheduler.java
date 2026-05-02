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
    @Transactional
    public void processPayments() {
        List<PaymentRecord> pending = paymentRecordService.findAllPending();

        if (pending.isEmpty()) {
            log.info("No pending payments found");
            return;
        }

        Map<UUID, List<PaymentRecord>> byUser = pending.stream()
                .collect(Collectors.groupingBy(PaymentRecord::getUserId));

        byUser.forEach((userId, records) -> {
            BigDecimal payment = records.stream()
                    .map(PaymentRecord::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            userBalanceService.updateAmount(userId, payment);
            paymentRecordService.updatePaymentRecord(records);
        });
    }
}
