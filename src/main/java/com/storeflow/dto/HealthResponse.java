package com.storeflow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for health check endpoint.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthResponse {
    
    @JsonProperty("status")
    private String status; // "UP" or "DOWN"
    
    @JsonProperty("timestamp")
    private String timestamp; // ISO_ZONED_DATE_TIME format
    
    @JsonProperty("jvmUptimeMs")
    private Long jvmUptimeMs; // JVM uptime in milliseconds
}
