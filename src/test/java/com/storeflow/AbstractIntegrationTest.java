package com.storeflow;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Abstract base class for Spring Boot integration tests
 * 
 * Provides:
 * - @SpringBootTest: Full Spring application context
 * - @AutoConfigureMockMvc: MockMvc for controller testing
 * - @Testcontainers: Manages PostgreSQL container lifecycle
 * - @Container: Isolated PostgreSQL instance per test class hierarchy
 * 
 * Container Isolation:
 * Each @Container instance is automatically isolated by Testcontainers.
 * Tests extending this class share ONE container instance.
 * Different test classes with their own @Container get SEPARATE isolated containers.
 * 
 * Database Configuration:
 * - PostgreSQL 15 (Alpine)
 * - Test database: storeflow_test
 * - Autonomous container management via @Container
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
public abstract class AbstractIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
        .withDatabaseName("storeflow_test")
        .withUsername("storeflow_user")
        .withPassword("storeflow_test_password");

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
}
