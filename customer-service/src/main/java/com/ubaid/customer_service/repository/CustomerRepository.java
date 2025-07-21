package com.ubaid.customer_service.repository;

import com.ubaid.customer_service.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByUserId(UUID userId);
}
