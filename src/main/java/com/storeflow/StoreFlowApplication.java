package com.storeflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * StoreFlow API - Main entry point for the Spring Boot application.
 * 
 * A production-grade Inventory & Order Management REST API built with
 * Spring Boot 3, Spring Data JPA, PostgreSQL, and Spring Security with JWT.
 * 
 * Features:
 * - Full CRUD REST endpoints for products, categories, orders, and users
 * - Authentication & authorization with JWT tokens
 * - Role-based access control (USER, ADMIN)
 * - Real-time WebSocket notifications with STOMP
 * - File upload/download support with image optimization
 * - Email notifications service
 * - Advanced search and pagination capabilities
 * - Comprehensive error handling and validation
 * - Production-ready middleware components
 * - 80%+ code coverage with JUnit 5 + Testcontainers
 */
@SpringBootApplication
@EnableScheduling
public class StoreFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreFlowApplication.class, args);
    }
}
