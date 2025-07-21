package com.ubaid.customer_service.service;

import com.ubaid.customer_service.dto.AddressDTO;
import com.ubaid.customer_service.dto.CustomerRequest;
import com.ubaid.customer_service.dto.CustomerResponse;
import com.ubaid.customer_service.exception.AddressNotFoundException;
import com.ubaid.customer_service.exception.CustomerNotFoundException;
import com.ubaid.customer_service.mapper.AddressMapper;
import com.ubaid.customer_service.mapper.CustomerMapper;
import com.ubaid.customer_service.model.Address;
import com.ubaid.customer_service.model.Customer;
import com.ubaid.customer_service.repository.AddressRepository;
import com.ubaid.customer_service.repository.CustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper userMapper;
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    public CustomerResponse saveCustomerDetails(CustomerRequest customerRequest, Jwt jwt) {
        Map<String, Object> claims=jwt.getClaims();

        Customer customer = findOrCreateCustomer(claims.get("sub").toString());
        customer.setEmail(claims.get("email").toString());
        List<Address> existingAddresses = customer.getAddress();
        List<AddressDTO> newAddresses = customerRequest.address();
        for (int i = 0; i < newAddresses.size(); i++) {
            AddressDTO addrDTO = newAddresses.get(i);
            Address address;
            if (i < existingAddresses.size()) {
                address = existingAddresses.get(i); // Update existing address
            } else {
                address = new Address(); // Create new address
                address.setCustomer(customer);
                existingAddresses.add(address);
            }
            if (addrDTO.houseNo() != null && !addrDTO.houseNo().isBlank()) address.setHouseNo(addrDTO.houseNo());
            if (addrDTO.country() != null && !addrDTO.country().isBlank()) address.setCountry(addrDTO.country());
            if (addrDTO.landmark() != null && !addrDTO.landmark().isBlank()) address.setLandmark(addrDTO.landmark());
            if (addrDTO.district() != null && !addrDTO.district().isBlank()) address.setDistrict(addrDTO.district());
            if (addrDTO.state() != null && !addrDTO.state().isBlank()) address.setState(addrDTO.state());
            if (addrDTO.pinCode() != null && !addrDTO.pinCode().isBlank()) address.setPinCode(addrDTO.pinCode());
            if (addrDTO.street() != null && !addrDTO.street().isBlank()) address.setStreet(addrDTO.street());
        }
        if(customerRequest.name()!=null&&!customerRequest.name().isBlank()) customer.setName(customerRequest.name());
        if(customerRequest.contact()!=null&&!customerRequest.contact().isBlank()) customer.setContact(customerRequest.contact());
        if(customerRequest.email()!=null&&!customerRequest.email().isBlank())customer.setEmail(claims.get("email").toString());
        Customer savedUser = customerRepository.save(customer);
        return userMapper.fromUserEntity(savedUser);
    }

    private Customer findOrCreateCustomer(String id){
        UUID user_id=UUID.fromString(id);
        Optional<Customer> customer= Optional.of(customerRepository.findByUserId(user_id).orElseGet(() -> new Customer(user_id)));
        return customer.get();
    }

    public CustomerResponse getCustomerByUserId(UUID userId){

        Optional<Customer> customer= customerRepository.findByUserId(userId);
        if(customer.isEmpty()){
            throw new CustomerNotFoundException("Customer not found");
        }
        return userMapper.fromUserEntity(customer.get());
    }

    public List<AddressDTO> getAddressByUserId( Jwt jwt){
        Map<String, Object>claims=jwt.getClaims();
        Optional<Customer> customer= customerRepository.findByUserId(UUID.fromString(claims.get("sub").toString()));
        if(customer.isEmpty()){
            throw new CustomerNotFoundException("Customer not found");
        }
        Customer customer1=customer.get();
        return
                customer1.getAddress().stream()
                        .map(addressMapper::toAddressDTO)
                        .toList();
    }
    public AddressDTO updateAddByAddId( AddressDTO addressDTO,Long id) {
        Address address=addressRepository.findById(id).orElseThrow(()->{
            return new RuntimeException("Address not found for Id: "+id);
        });
        setIfNotNull(addressDTO.country(), address::setCountry);
        setIfNotNull(addressDTO.district(), address::setDistrict);
        setIfNotNull(addressDTO.houseNo(),address::setHouseNo);
        setIfNotNull(addressDTO.landmark(),address::setLandmark);
        setIfNotNull(addressDTO.state(),address::setState);
        setIfNotNull(addressDTO.street(),address::setStreet);
        setIfNotNull(addressDTO.pinCode(),address::setPinCode);
        Address updatedAddress=addressRepository.save(address);
        return addressMapper.toAddressDTO(updatedAddress);
    }
    private <T> void setIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }
    public void deleteUser(Jwt jwt) {
        Map<String,Object>claims=jwt.getClaims();
        Customer customer=  customerRepository.findByUserId(UUID.fromString(claims.get("sub").toString())).get();
        customerRepository.deleteById(customer.getCustomerId());
    }

    public void deleteAddress(Long id) {
        addressRepository.findById(id).ifPresentOrElse(
                address -> addressRepository.deleteById(id),()-> {
                    throw new AddressNotFoundException("Address  not Found with the given Id: " + id);
                });
    }
}
