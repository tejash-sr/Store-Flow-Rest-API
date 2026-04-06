package com.storeflow.controller;

import com.storeflow.dto.HealthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Health check endpoint for StoreFlow API.
 * 
 * Used by load balancers, monitoring systems, and clients to verify
 * API availability and JVM uptime.
 */
@Slf4j
@RestController
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<HealthResponse> getHealth() {
        log.debug("Health check requested");
        
        HealthResponse response = HealthResponse.builder()
            .status("UP")
            .timestamp(Instant.now().toString())
            .jvmUptimeMs(getJvmUptimeMs())
            .build();
        
        return ResponseEntity.ok(response);
    }

    private long getJvmUptimeMs() {
        return java.lang.management.ManagementFactory.getRuntimeMXBean().getUptime();
    }
}
