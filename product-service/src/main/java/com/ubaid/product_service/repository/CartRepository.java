package com.ubaid.product_service.repository;

import com.ubaid.product_service.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<CartItem,Long> {


    Optional<CartItem> findByUserId(UUID sub);
}
