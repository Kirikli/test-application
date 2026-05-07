package com.example.accounting_service.repository;

import asyncapi.enums.PaymentStatus;
import com.example.accounting_service.model.PaymentRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, UUID> {

    Page<PaymentRecord> findAllByUserId(UUID userId, Pageable pageable);

    Page<PaymentRecord> findAllByStatus(PaymentStatus status, Pageable pageable);

    @Modifying
    @Query(value = """
            insert into payment_record (task_id, user_id, amount, date, status)
            values (:taskId, :userId, :amount, :date, :status)
            on CONFLICT (task_id) DO NOTHING
            """, nativeQuery = true)
    void insertIfNotExists(UUID taskId,
                           UUID userId,
                           BigDecimal amount,
                           Instant date,
                           String status);

    @Modifying
    @Query("""
                update PaymentRecord p
                   set p.status = :status
                 where p.id in :ids
            """)
    void updateStatusByIds(List<UUID> ids, PaymentStatus status);
}
