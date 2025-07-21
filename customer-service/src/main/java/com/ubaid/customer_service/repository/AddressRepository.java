package com.ubaid.customer_service.repository;

import com.ubaid.customer_service.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
