package com.storeflow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for ShippingAddress embeddable
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingAddressDTO {

    private String street;
    private String city;
    private String country;
    private String postalCode;
}
