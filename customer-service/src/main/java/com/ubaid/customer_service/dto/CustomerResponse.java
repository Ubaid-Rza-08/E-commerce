package com.ubaid.customer_service.dto;

import java.util.UUID;

public record CustomerResponse(
        UUID userId,
        String name,
        String email,
        String contact
) {
}
