package com.example.search_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        jdbcTemplate.execute("CREATE EXTENSION IF NOT EXISTS pg_trgm");
        jdbcTemplate.execute("""
                CREATE INDEX IF NOT EXISTS idx_users_email_trgm
                ON users USING GIN (email gin_trgm_ops)
                """);
    }
}
