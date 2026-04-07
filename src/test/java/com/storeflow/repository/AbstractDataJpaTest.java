package com.storeflow.repository;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Abstract base class for repository slice tests using @DataJpaTest and Testcontainers
 * 
 * Provides:
 * - @DataJpaTest: Slice testing with JPA/Hibernate focus
 * - PostgreSQL 15 via Testcontainers (NOT H2 - per exercise requirements)
 * - Isolated @Container instance from integration tests
 * - Database isolation via separate @Container per test class
 * 
 * Container Isolation Model:
 * - Each test class with @Container gets its own autonomous PostgreSQL container
 * - AbstractIntegrationTest @Container → isolated from this one
 * - Other repository test classes @Container → isolated from each other
 * - No collision because each @Container is a separate instance
 * 
 * Exercise Requirement (Phase 2.4):
 * "Test each entity and repository in isolation using @DataJpaTest and Testcontainers"
 */
@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class AbstractDataJpaTest {
    
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
