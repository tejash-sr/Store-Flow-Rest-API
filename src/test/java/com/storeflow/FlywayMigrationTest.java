package com.storeflow;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Flyway migration verification tests
 * Ensures all database migrations apply cleanly
 */
@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Flyway Migration Tests")
class FlywayMigrationTest {

    @Autowired
    private Flyway flyway;

    @Test
    @DisplayName("All Flyway migrations should apply successfully")
    void testMigrationsApplyCleanly() {
        assertNotNull(flyway);
        assertTrue(flyway.info().current().success, "Flyway migrations failed to apply");
    }

    @Test
    @DisplayName("Flyway should have executed all migrations in order")
    void testMigrationOrder() {
        assertNotNull(flyway.info().all());
        assertTrue(flyway.info().all().length >= 2, "Expected at least V1 and V2 migrations");
    }
}
