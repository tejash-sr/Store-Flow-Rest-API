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
 * Prerequisites:
 * - PostgreSQL container must be running (start with: docker-compose -f docker-compose.test.yml up)
 * - Container listens on localhost:5433 with database: storeflow_rest_api
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
    // No container management - assumes docker-compose.test.yml is running
    // Tests connect to localhost:5433 (configured in application-test.yml)
}
