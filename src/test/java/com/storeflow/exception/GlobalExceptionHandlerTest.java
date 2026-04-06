package com.storeflow.exception;

import com.storeflow.dto.ErrorResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for GlobalExceptionHandler.
 * 
 * Tests exception handling and error response generation.
 */
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@DisplayName("GlobalExceptionHandler Unit Tests")
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @Test
    @DisplayName("Handle 404 NotFound returns HTTP 404 with error response")
    void testHandleNotFound_Returns404() throws Exception {
        NoHandlerFoundException ex = new NoHandlerFoundException("GET", "/api/nonexistent", null);
        
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Not Found", response.getBody().getError());
        assertTrue(response.getBody().getMessage().contains("Resource not found"));
    }

    @Test
    @DisplayName("Handle generic exception returns HTTP 500")
    void testHandleGenericException_Returns500() {
        RuntimeException ex = new RuntimeException("Unexpected error");
        
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleGenericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().getStatus());
        assertEquals("Internal Server Error", response.getBody().getError());
    }

    @Test
    @DisplayName("Error response includes timestamp in ISO format")
    void testErrorResponse_IncludesTimestamp() throws Exception {
        NoHandlerFoundException ex = new NoHandlerFoundException("GET", "/api/test", null);
        
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleNotFound(ex);

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getTimestamp());
        assertTrue(response.getBody().getTimestamp().contains("T"));
    }
}
