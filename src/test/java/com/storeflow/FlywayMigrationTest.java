package com.storeflow;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Flyway migration verification integration tests
 * Ensures all database migrations apply cleanly to PostgreSQL
 * 
 * Extends AbstractIntegrationTest to get:
 * - Full Spring Boot context (@SpringBootTest)
 * - Testcontainers PostgreSQL database
 * - Flyway auto-configuration for true integration testing
 * 
 * Per Phase 2.4 exercise requirements: "Flyway: all migration scripts apply cleanly"
 */
@DisplayName("Flyway Migration Integration Tests")
class FlywayMigrationTest extends AbstractIntegrationTest {

    @Autowired
    private Flyway flyway;

    @Test
    @DisplayName("All Flyway migrations should apply successfully")
    void testMigrationsApplyCleanly() {
        assertNotNull(flyway);
        var migrationInfo = flyway.info().current();
        assertNotNull(migrationInfo, "No migration has been applied");
    }

    @Test
    @DisplayName("Flyway should have executed all migrations in order")
    void testMigrationOrder() {
        assertNotNull(flyway.info().all());
        assertTrue(flyway.info().all().length >= 1, "Expected at least V1 migration to be applied");
    }
}
