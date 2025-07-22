package com.ubaid.customer_service.controller;

import com.ubaid.customer_service.dto.AddressDTO;
import com.ubaid.customer_service.dto.CustomerRequest;
import com.ubaid.customer_service.dto.CustomerResponse;
import com.ubaid.customer_service.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;
    @PostMapping("/saveCustomerDetails")
    public ResponseEntity<CustomerResponse> saveCustomerDetails(@RequestBody @Valid CustomerRequest customer, @AuthenticationPrincipal Jwt jwt){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.saveCustomerDetails(customer,jwt));
    }
    @GetMapping("/address")
    public ResponseEntity<List<AddressDTO>>getAddressByUserId(@AuthenticationPrincipal Jwt jwt){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAddressByUserId(jwt));
    }
    @GetMapping("/getCustomer")
    public ResponseEntity<CustomerResponse>getCustomerByUserId(@RequestParam UUID UserId){
        return ResponseEntity.status(HttpStatus.OK).body(service.getCustomerByUserId(UserId));
    }
    @PutMapping("/update/address/{id}")
    public ResponseEntity<AddressDTO>updateAddByAddId(@Valid  @RequestBody AddressDTO addressDTO,@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.updateAddByAddId(addressDTO,id));
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal Jwt jwt){
        service.deleteUser(jwt);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/address/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id){
        service.deleteAddress(id);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
