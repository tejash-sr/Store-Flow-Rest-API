package com.storeflow.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ShippingAddress embeddable value object
 * Represents the physical address for order delivery
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingAddress {

    @Column(name = "shipping_street", nullable = false, length = 255)
    private String street;

    @Column(name = "shipping_city", nullable = false, length = 100)
    private String city;

    @Column(name = "shipping_country", nullable = false, length = 100)
    private String country;

    @Column(name = "shipping_postal_code", nullable = false, length = 20)
    private String postalCode;
}
