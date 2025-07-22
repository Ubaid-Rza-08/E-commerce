package com.ubaid.product_service.repository;

import com.ubaid.product_service.dto.ProductResponse;
import com.ubaid.product_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByIdInOrderById(List<Long> ids);
    @Query("SELECT p FROM Product p WHERE p.category.id=:id ")
    List<ProductResponse> findAllByCategoryId(Long id);
}
