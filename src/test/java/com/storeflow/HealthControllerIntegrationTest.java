package com.storeflow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for HealthController.
 * 
 * Tests the full Spring Boot application context with MockMvc,
 * verifying HTTP responses, headers, and JSON structure.
 */
@DisplayName("HealthController Integration Tests")
class HealthControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/health returns HTTP 200 with correct JSON structure")
    void testHealthEndpoint_Returns200() throws Exception {
        mockMvc.perform(get("/api/health"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value("UP"))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.jvmUptimeMs").isNumber());
    }

    @Test
    @DisplayName("GET /api/health includes X-Trace-Id response header")
    void testHealthEndpoint_IncludesTraceIdHeader() throws Exception {
        mockMvc.perform(get("/api/health"))
            .andExpect(status().isOk())
            .andExpect(header().exists("X-Trace-Id"))
            .andExpect(header().string("X-Trace-Id", matchesPattern("[0-9a-f\\-]+")));
    }

    @Test
    @DisplayName("GET /api/nonexistent returns HTTP 404 with error response")
    void testNonexistentEndpoint_Returns404() throws Exception {
        mockMvc.perform(get("/api/nonexistent"))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.error").value("Not Found"))
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("GET /api/health with Origin header includes CORS headers")
    void testHealthEndpoint_IncludesCORSHeaders() throws Exception {
        mockMvc.perform(get("/api/health")
                .header("Origin", "http://localhost:3000"))
            .andExpect(status().isOk())
            .andExpect(header().exists("Access-Control-Allow-Origin"));
    }
}
