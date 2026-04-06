package com.storeflow.enums;

/**
 * Order status enumeration
 * PENDING: Order created but not yet confirmed
 * CONFIRMED: Order has been confirmed by admin
 * SHIPPED: Order is in transit
 * DELIVERED: Order has been delivered to customer
 * CANCELLED: Order has been cancelled
 */
public enum OrderStatus {
    PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
}
