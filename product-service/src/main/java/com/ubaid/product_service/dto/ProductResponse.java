package com.ubaid.product_service.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String image,
        String description,
        double stock,
        BigDecimal price,
        String ingredients
) {
}
