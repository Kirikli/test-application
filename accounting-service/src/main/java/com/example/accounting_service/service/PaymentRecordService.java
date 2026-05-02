package com.example.accounting_service.service;

import asyncapi.enums.PaymentStatus;
import asyncapi.event.TaskCompleteEvent;
import asyncapi.util.PageResponseBuilder;
import asyncapi.util.PageResponseDTO;
import com.example.accounting_service.dto.PaymentEventDTO;
import com.example.accounting_service.dto.PaymentRecordDTO;
import com.example.accounting_service.mapper.PaymentRecordMapper;
import com.example.accounting_service.model.PaymentRecord;
import com.example.accounting_service.repository.PaymentRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentRecordService {

    private final PaymentRecordMapper paymentRecordMapper;
    private final PaymentRecordRepository paymentRecordRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void create(TaskCompleteEvent completeEvent) {
        PaymentRecord paymentRecord = new PaymentRecord();
        paymentRecord.setAmount(completeEvent.amount());
        paymentRecord.setStatus(PaymentStatus.PENDING);
        paymentRecord.setDate(completeEvent.date());
        paymentRecord.setTaskId(completeEvent.taskId());
        paymentRecord.setUserId(completeEvent.executorId());

        paymentRecordRepository.save(paymentRecord);
    }

    @Transactional
    public void updatePaymentRecord(List<PaymentRecord> paymentRecords) {
        paymentRecords.forEach(record -> {
            record.setStatus(PaymentStatus.PAID);
            paymentRecordRepository.save(record);

            eventPublisher.publishEvent(
                    new PaymentEventDTO(
                            record.getId(),
                            record.getUserId(),
                            record.getAmount(),
                            record.getDate(),
                            record.getTaskId()
                    )
            );
        });
    }

    @Transactional(readOnly = true)
    public PageResponseDTO<PaymentRecordDTO> getUserPayments(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), size);
        Page<PaymentRecord> payments = paymentRecordRepository.findAllByUserId(userId, pageable);

        return PageResponseBuilder.of(
                payments.getContent(),
                payments.getTotalElements(),
                payments.getTotalPages(),
                paymentRecordMapper::toDto
        );
    }

    @Transactional(readOnly = true)
    public List<PaymentRecord> findAllPending() {
        return paymentRecordRepository.findAllByStatus(PaymentStatus.PENDING);
    }
}
