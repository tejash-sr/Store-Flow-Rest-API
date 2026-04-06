package com.storeflow.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Standardized error response DTO for all API error scenarios.
 * 
 * Used by GlobalExceptionHandler to return consistent error information
 * to clients on all failure paths (400, 401, 403, 404, 409, 422, 500, etc.).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    
    @JsonProperty("timestamp")
    private String timestamp; // ISO_ZONED_DATE_TIME format
    
    @JsonProperty("status")
    private Integer status; // HTTP status code (400, 401, 404, 500, etc.)
    
    @JsonProperty("error")
    private String error; // Brief error name (e.g., "Not Found", "Bad Request")
    
    @JsonProperty("message")
    private String message; // Detailed error description
    
    @JsonProperty("path")
    private String path; // Request path or resource ID that caused the error
    
    @JsonProperty("errors")
    private Map<String, String> fieldErrors; // Field-level validation errors map
}
