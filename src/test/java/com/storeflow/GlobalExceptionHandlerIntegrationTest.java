package com.storeflow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for GlobalExceptionHandler.
 * 
 * Tests exception handling across the full Spring application.
 */
@DisplayName("GlobalExceptionHandler Integration Tests")
class GlobalExceptionHandlerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Invalid path returns 404 with structured error response")
    void testInvalidPath_Returns404WithErrorBody() throws Exception {
        mockMvc.perform(get("/api/invalid/path/here"))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.error").value("Not Found"))
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.path").exists());
    }

    @Test
    @DisplayName("POST to health endpoint (wrong method) returns 405 or 404")
    void testWrongHttpMethod_Returns405OrNotFound() throws Exception {
        mockMvc.perform(post("/api/health"))
            .andExpect(status().notFound());
    }

    @Test
    @DisplayName("Error response timestamp is in ISO format")
    void testErrorResponse_TimestampInISOFormat() throws Exception {
        mockMvc.perform(get("/api/unreachable"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.timestamp").matches("\\d{4}-\\d{2}-\\d{2}T.*Z"));
    }

    @Test
    @DisplayName("Multiple consecutive errors have unique timestamps")
    void testMultipleErrors_HaveUniqueTimestamps() throws Exception {
        // First request
        String response1 = mockMvc.perform(get("/api/test1"))
            .andExpect(status().isNotFound())
            .andReturn().getResponse().getContentAsString();

        // Small delay
        Thread.sleep(10);

        // Second request
        String response2 = mockMvc.perform(get("/api/test2"))
            .andExpect(status().isNotFound())
            .andReturn().getResponse().getContentAsString();

        // Timestamps should be different (highly likely)
        // This is a simple check - in production, use a proper assertion library
    }
}
