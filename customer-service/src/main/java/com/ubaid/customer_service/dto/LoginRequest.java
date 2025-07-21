package com.ubaid.customer_service.dto;

public record LoginRequest(
        String email,
        String password
) {
}