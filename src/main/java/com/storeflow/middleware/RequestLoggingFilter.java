package com.storeflow.middleware;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * RequestLoggingFilter - Middleware Priority 1 (executes first).
 * 
 * Responsibilities:
 * - Generate a unique UUID trace ID for every incoming request
 * - Store it in MDC (MDC.put("traceId", traceId)) so it appears in every log line
 * - Add it to the response as X-Trace-Id header for client-side correlation
 * - Log HTTP method, URI, response status, duration in ms
 * - Clear MDC after request completes (MDC.clear())
 * 
 * Why it matters: Without this, debugging production issues is nearly impossible—
 * you cannot correlate log lines from the same request.
 * 
 * Current Phase: Phase 1 (placeholder - full logging comes later)
 * Full Implementation: Phase 8
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final String TRACE_ID_HEADER = "X-Trace-Id";

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        
        String traceId = UUID.randomUUID().toString();
        MDC.put("traceId", traceId);
        response.setHeader(TRACE_ID_HEADER, traceId);

        long startTime = System.currentTimeMillis();
        
        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            log.info("HTTP {} {} -> {} ({}ms)",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                duration
            );
            MDC.clear();
        }
    }
}
