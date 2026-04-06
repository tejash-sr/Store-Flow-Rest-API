package com.storeflow.middleware;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for RequestLoggingFilter.
 * 
 * Tests UUID generation, MDC management, and header injection.
 */
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@DisplayName("RequestLoggingFilter Unit Tests")
class RequestLoggingFilterTest {

    @InjectMocks
    private RequestLoggingFilter requestLoggingFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Test
    @DisplayName("Filter adds X-Trace-Id header to response")
    void testFilter_AddsXTraceIdHeader() throws Exception {
        requestLoggingFilter.doFilterInternal(request, response, filterChain);

        verify(response).setHeader(eq("X-Trace-Id"), anyString());
    }

    @Test
    @DisplayName("Filter clears MDC after request completes")
    void testFilter_ClearsMDCAfterRequest() throws Exception {
        // Set up MDC before filter
        MDC.put("traceId", "test-id");
        
        // After filter execution, MDC should be cleared
        requestLoggingFilter.doFilterInternal(request, response, filterChain);

        // MDC.get returns null if cleared
        assertNull(MDC.get("traceId"));
    }

    @Test
    @DisplayName("Filter calls doFilter to continue chain")
    void testFilter_ContinuesFilterChain() throws Exception {
        requestLoggingFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }
}
