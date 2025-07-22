package com.ubaid.product_service.dto;

import java.math.BigDecimal;

public record PurchaseResponse(
        Long productId,
        String name,
        String description,
        BigDecimal price,
        double quantity
) {
}
