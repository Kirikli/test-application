package com.example.search_service.repository;

import com.example.search_service.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Modifying
    @Query(value = """
            insert into users (id, email, name, birthday)
            values (:userId, :email, :name, :birthday)
            on CONFLICT (id) DO NOTHING
            """, nativeQuery = true)
    void insertIfNotExists(UUID userId, String email, String name, LocalDate birthday);

    @Query(value = """
            SELECT u.*
            FROM (
                SELECT *, similarity(email, :email) AS sim
                FROM users
            ) u
            WHERE u.sim > :threshold
            ORDER BY u.sim DESC
            """,
            countQuery = """
            SELECT count(*)
            FROM users u
            WHERE similarity(u.email, :email) > :threshold
            """,
            nativeQuery = true)
    Page<User> searchByEmailFuzzy(
            String email,
            double threshold,
            Pageable pageable
    );
}
