package com.ubaid.customer_service.dto;

import java.util.List;

public record CustomerRequest(
        String name,
        String email,
        String contact,
        List<AddressDTO> address
) {
}
