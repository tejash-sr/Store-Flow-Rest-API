package com.storeflow.dto;

import com.storeflow.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * DTO for Order entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private Long id;
    private String referenceNumber;
    private Long customerId;
    private OrderStatus status;
    private ShippingAddressDTO shippingAddress;
    private BigDecimal totalAmount;
    private List<OrderItemDTO> orderItems;
    private Instant createdAt;
    private Instant updatedAt;
}
