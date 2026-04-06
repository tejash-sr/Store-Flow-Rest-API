package com.storeflow.controller;

import com.storeflow.dto.HealthResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for HealthController.
 * 
 * Tests the health check endpoint in isolation without a full Spring context.
 */
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@DisplayName("HealthController Unit Tests")
class HealthControllerTest {

    @InjectMocks
    private HealthController healthController;

    @Test
    @DisplayName("GET /api/health returns HTTP 200 with status UP")
    void testGetHealth_ReturnsStatusUP() {
        ResponseEntity<HealthResponse> response = healthController.getHealth();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("UP", response.getBody().getStatus());
    }

    @Test
    @DisplayName("GET /api/health includes ISO formatted timestamp")
    void testGetHealth_IncludesTimestamp() {
        ResponseEntity<HealthResponse> response = healthController.getHealth();

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getTimestamp());
        assertTrue(response.getBody().getTimestamp().contains("T"));
        assertTrue(response.getBody().getTimestamp().contains("Z"));
    }

    @Test
    @DisplayName("GET /api/health includes positive JVM uptime")
    void testGetHealth_IncludesJvmUptime() {
        ResponseEntity<HealthResponse> response = healthController.getHealth();

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getJvmUptimeMs());
        assertGreater(response.getBody().getJvmUptimeMs(), 0L);
    }

    private void assertGreater(Long actual, long expected) {
        assertTrue(actual > expected, actual + " should be greater than " + expected);
    }
}
