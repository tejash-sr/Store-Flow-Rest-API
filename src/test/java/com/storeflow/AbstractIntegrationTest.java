package com.storeflow;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Base class for all integration tests in StoreFlow API.
 * 
 * Automatically configures:
 * - @SpringBootTest: Loads full Spring application context
 * - @Testcontainers: Manages real PostgreSQL container lifecycle
 * - @AutoConfigureMockMvc: Configures MockMvc for controller testing
 * - @ActiveProfiles("test"): Uses application-test.yml configuration
 * - PostgreSQL container: Real database instance per test class
 * 
 * All integration tests must extend this class to inherit the container setup.
 * 
 * Example:
 * <pre>
 * @SpringBootTest
 * class ProductRepositoryIntegrationTest extends AbstractIntegrationTest {
 *     @Inject MockMvc mvc;
 *     
 *     @Test
 *     void testFindBySkuIgnoreCase() {
 *         // Test code with real database...
 *     }
 * }
 * </pre>
 */
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
        .withDatabaseName("storeflow_rest_api")
        .withUsername("storeflow_user")
        .withPassword("storeflow_test_password")
        .withLabel("app", "storeflow-rest-api")
        .withLabel("phase", "phase-1-foundation")
        .withLabel("environment", "test");

    /**
     * Dynamically inject PostgreSQL container properties into Spring context.
     * This ensures Spring uses the running container instead of localhost:5432.
     */
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
}
