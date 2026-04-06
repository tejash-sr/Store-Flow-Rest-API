package com.storeflow.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ErrorResponse utility class.
 * 
 * Tests the ErrorResponse builder and field validation with multiple scenarios.
 */
@DisplayName("ErrorResponse Utility Class Tests")
class ErrorResponseTest {

    @Test
    @DisplayName("Build complete ErrorResponse with all fields")
    void testBuildCompleteErrorResponse() {
        // Arrange
        String timestamp = "2024-01-01T12:00:00Z";
        int status = 400;
        String error = "Bad Request";
        String message = "Validation failed";
        String path = "/api/users";
        Map<String, String> fieldErrors = new HashMap<>();
        fieldErrors.put("email", "Email must be valid");
        fieldErrors.put("name", "Name is required");

        // Act
        ErrorResponse response = ErrorResponse.builder()
            .timestamp(timestamp)
            .status(status)
            .error(error)
            .message(message)
            .path(path)
            .fieldErrors(fieldErrors)
            .build();

        // Assert
        assertNotNull(response);
        assertEquals(timestamp, response.getTimestamp());
        assertEquals(status, response.getStatus());
        assertEquals(error, response.getError());
        assertEquals(message, response.getMessage());
        assertEquals(path, response.getPath());
        assertEquals(2, response.getFieldErrors().size());
        assertEquals("Email must be valid", response.getFieldErrors().get("email"));
        assertEquals("Name is required", response.getFieldErrors().get("name"));
    }

    @Test
    @DisplayName("Build ErrorResponse with minimal fields (no fieldErrors)")
    void testBuildMinimalErrorResponse() {
        // Arrange
        String timestamp = "2024-01-01T12:05:00Z";
        int status = 404;
        String error = "Not Found";
        String message = "Resource not found";
        String path = "/api/products/999";

        // Act
        ErrorResponse response = ErrorResponse.builder()
            .timestamp(timestamp)
            .status(status)
            .error(error)
            .message(message)
            .path(path)
            .build();

        // Assert
        assertNotNull(response);
        assertEquals(timestamp, response.getTimestamp());
        assertEquals(status, response.getStatus());
        assertEquals(error, response.getError());
        assertEquals(message, response.getMessage());
        assertEquals(path, response.getPath());
        assertTrue(response.getFieldErrors() == null || response.getFieldErrors().isEmpty());
    }

    @Test
    @DisplayName("Build ErrorResponse with empty fieldErrors map")
    void testBuildErrorResponseWithEmptyFieldErrors() {
        // Arrange
        String timestamp = "2024-01-01T12:10:00Z";
        int status = 500;
        String error = "Internal Server Error";
        String message = "An unexpected error occurred";
        Map<String, String> fieldErrors = new HashMap<>();

        // Act
        ErrorResponse response = ErrorResponse.builder()
            .timestamp(timestamp)
            .status(status)
            .error(error)
            .message(message)
            .fieldErrors(fieldErrors)
            .build();

        // Assert
        assertNotNull(response);
        assertEquals(timestamp, response.getTimestamp());
        assertEquals(status, response.getStatus());
        assertEquals(0, response.getFieldErrors().size());
    }

    @Test
    @DisplayName("ErrorResponse with null path (acceptable for generic errors)")
    void testBuildErrorResponseWithNullPath() {
        // Arrange
        String timestamp = "2024-01-01T12:15:00Z";
        int status = 500;
        String error = "Internal Server Error";
        String message = "Unexpected error occurred";

        // Act
        ErrorResponse response = ErrorResponse.builder()
            .timestamp(timestamp)
            .status(status)
            .error(error)
            .message(message)
            .path(null)
            .build();

        // Assert
        assertNotNull(response);
        assertEquals(timestamp, response.getTimestamp());
        assertNull(response.getPath());
        assertEquals(status, response.getStatus());
    }

    @Test
    @DisplayName("ErrorResponse timestamp must be non-null and non-empty")
    void testErrorResponseTimestampValidation() {
        // Act
        ErrorResponse response = ErrorResponse.builder()
            .timestamp("2024-01-01T12:20:00Z")
            .status(400)
            .error("Bad Request")
            .message("Invalid input")
            .build();

        // Assert
        assertNotNull(response.getTimestamp());
        assertFalse(response.getTimestamp().isEmpty());
        assertTrue(response.getTimestamp().contains("T"), "Timestamp should be in ISO format");
    }
}
