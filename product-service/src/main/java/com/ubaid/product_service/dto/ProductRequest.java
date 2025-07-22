package com.ubaid.product_service.dto;

import java.math.BigDecimal;

public record ProductRequest(
        String name,
        String image,
        String description,
        double stock,
        BigDecimal price,
        String ingredients
) {
}

