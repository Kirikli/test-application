package com.example.accounting_service.repository;

import asyncapi.enums.PaymentStatus;
import com.example.accounting_service.model.PaymentRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, UUID> {

    Page<PaymentRecord> findAllByUserId(UUID userId, Pageable pageable);

    List<PaymentRecord> findAllByStatus(PaymentStatus status);
}
