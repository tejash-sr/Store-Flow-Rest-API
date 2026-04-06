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
 * Base class for all integration tests in StoreFlow API.
 * 
 * Automatically configures:
 * - @SpringBootTest: Loads full Spring application context
 * - @AutoConfigureMockMvc: Configures MockMvc for controller testing
 * - @Testcontainers: Manages PostgreSQL container lifecycle automatically
 * - @Container: Spins up PostgreSQL 15-alpine for each test class
 * - @DynamicPropertySource: Injects container connection details into Spring
 * - @ActiveProfiles("test"): Uses application-test.yml configuration
 * 
 * Container Setup (Automated):
 * - PostgreSQL 15-alpine container starts automatically before tests run
 * - Database: storeflow_rest_api
 * - Username: storeflow_user
 * - Password: storeflow_test_password
 * - Container is destroyed after all tests complete
 * - Works on Windows, Linux, WSL2 without manual Docker setup
 * 
 * All integration tests must extend this class to inherit the base setup.
 * 
 * Example:
 * <pre>
 * class ProductRepositoryIntegrationTest extends AbstractIntegrationTest {
 *     @Test
 *     void testFindBySkuIgnoreCase() {
 *         // Test code with real database...
 *     }
 * }
 * </pre>
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
public abstract class AbstractIntegrationTest {
    
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
        .withDatabaseName("storeflow_rest_api")
        .withUsername("storeflow_user")
        .withPassword("storeflow_test_password");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
}
