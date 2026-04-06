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
 * JwtAuthenticationFilter - Middleware Priority 3.
 * 
 * Registered via addFilterBefore(UsernamePasswordAuthenticationFilter.class)
 * 
 * Responsibilities:
 * - Read the Authorization header
 * - If it starts with "Bearer ", extract the token string
 * - Call JwtUtil.extractUsername(token) — this throws JwtException for invalid/expired tokens
 * - If valid: load UserDetails, set UsernamePasswordAuthenticationToken in SecurityContextHolder
 * - If no header, or not a Bearer token: pass through without setting authentication
 * - If JWT is invalid/expired: return 401 with structured error body
 * 
 * Why it matters: This is the security gate for the entire API. Without it,
 * JWT tokens are meaningless.
 * 
 * Current Phase: Phase 1 (placeholder - full implementation in Phase 4)
 * Full Implementation: Phase 4
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        
        // Phase 1: Placeholder that allows all requests
        // Full implementation with JWT validation will be added in Phase 4
        filterChain.doFilter(request, response);
    }

    @SuppressWarnings("unused")
    private void sendUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("""
            {
              "timestamp": "%s",
              "status": 401,
              "error": "Unauthorized",
              "message": "%s"
            }
            """.formatted(System.currentTimeMillis(), message));
        response.getWriter().flush();
    }
}
