package com.storeflow;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Base class for all integration tests in StoreFlow API.
 * 
 * Automatically configures:
 * - @SpringBootTest: Loads full Spring application context
 * - @AutoConfigureMockMvc: Configures MockMvc for controller testing
 * - @ActiveProfiles("test"): Uses application-test.yml configuration
 * 
 * Container Setup:
 * - PostgreSQL container must be running via: docker-compose -f docker-compose.test.yml up
 * - Database: storeflow_rest_api
 * - Username: storeflow_user (configurable via env vars)
 * - Password: storeflow_test_password (configurable via env vars)
 * - Port: 5433
 * 
 * Why manual docker-compose instead of @Testcontainers:
 * - Windows Docker Desktop has socket connection limitations with Testcontainers
 * - docker-compose.test.yml provides reliable, reusable container management
 * - Integrates with CI/CD pipelines more predictably
 * - Container persists across test runs for faster development cycles
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
public abstract class AbstractIntegrationTest {
    // Container is managed externally via docker-compose.test.yml
    // Tests connect to localhost:5433 (configured in application-test.yml)
    // Start with: docker-compose -f docker-compose.test.yml up
}
