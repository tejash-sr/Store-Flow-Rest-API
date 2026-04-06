package com.storeflow.middleware;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * RateLimitingFilter - Middleware Priority 2.
 * 
 * Responsibilities:
 * - Use Bucket4j token bucket algorithm, keyed by remote IP address
 * - Allow maximum 5 requests per 15-minute window per IP
 * - On limit exceeded: return 429 Too Many Requests
 * - Bypass all non-auth routes (proceed to next filter without checking)
 * 
 * Why it matters: Without rate limiting, auth endpoints are vulnerable to
 * brute-force credential stuffing attacks.
 * 
 * Current Phase: Phase 1 (placeholder - full implementation in Phase 4)
 * Full Implementation: Phase 4
 */
@Slf4j
@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        
        // Phase 1: Placeholder that allows all requests
        // Full implementation with Bucket4j will be added in Phase 4
        filterChain.doFilter(request, response);
    }

    @SuppressWarnings("unused")
    private void sendRateLimitExceeded(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("""
            {
              "error": "Too many requests. Try again later."
            }
            """);
        response.getWriter().flush();
    }
}
