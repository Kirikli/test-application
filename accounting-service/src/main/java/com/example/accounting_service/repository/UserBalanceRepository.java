package com.example.accounting_service.repository;

import com.example.accounting_service.model.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Repository
public interface UserBalanceRepository extends JpaRepository<UserBalance, UUID> {

    @Modifying
    @Query(value = """
            insert into user_balance (id, balance, updated_at)
            values (:userId, 0, :updatedAt)
            on CONFLICT (id) DO NOTHING
            """, nativeQuery = true)
    void insertIfNotExists(UUID userId, Instant updatedAt);

    @Modifying
    @Query("""
                update UserBalance ub
                   set ub.balance = ub.balance + :delta,
                       ub.updatedAt = :updatedAt
                 where ub.userId = :userId
            """)
    void updateBalance(UUID userId,
                       BigDecimal delta,
                       Instant updatedAt);
}
