package com.storeflow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for middleware and security configuration.
 * 
 * Tests RequestLoggingFilter, CORS headers, and security baseline.
 */
@DisplayName("Middleware & Security Integration Tests")
class MiddlewareSecurityIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Every response includes X-Trace-Id header")
    void testEveryResponse_IncludesXTraceIdHeader() throws Exception {
        mockMvc.perform(get("/api/health"))
            .andExpect(status().isOk())
            .andExpect(header().exists("X-Trace-Id"));
    }

    @Test
    @DisplayName("Health endpoint is publicly accessible without authentication")
    void testHealthEndpoint_IsPubliclyAccessible() throws Exception {
        mockMvc.perform(get("/api/health"))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("CORS allows requests from configured localhost origins")
    void testCORS_AllowsConfiguredOrigins() throws Exception {
        mockMvc.perform(get("/api/health")
                .header("Origin", "http://localhost:3000"))
            .andExpect(status().isOk())
            .andExpect(header().exists("Access-Control-Allow-Origin"));
    }

    @Test
    @DisplayName("Actuator health endpoint is accessible")
    void testActuatorHealth_IsAccessible() throws Exception {
        mockMvc.perform(get("/actuator/health"))
            .andExpect(status().isOk());
    }
}
