package com.ubaid.customer_service.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.NonNull;

public record AddressDTO(
        Long id,
        @NotEmpty(message="Provide House Number")
        String houseNo,
        @NotEmpty(message="Provide street ")
        String street,
        @NotEmpty(message="Provide landmark")
        String landmark,
        @NotEmpty(message="Provide District")
        String district,
        @NotEmpty(message="Provide State")
        String state,
        @NotEmpty(message="Provide Country")
        String country,
        @NotEmpty(message="Provide Pincode")
        String pinCode
) {
}
