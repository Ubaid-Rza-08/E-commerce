package com.ubaid.customer_service.mapper;

import com.ubaid.customer_service.dto.AddressDTO;
import com.ubaid.customer_service.dto.CustomerRequest;
import com.ubaid.customer_service.dto.CustomerResponse;
import com.ubaid.customer_service.model.Address;
import com.ubaid.customer_service.model.Customer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerMapper {
    public Customer toUserEntity(CustomerRequest req) {
        if (req == null) return null;
        List<Address> addressList = new ArrayList<>();
        if (req.address() != null) {
            for (AddressDTO addrReq : req.address()) {
                Address address = new Address();
                address.setHouseNo(addrReq.houseNo());
                address.setStreet(addrReq.street());
                address.setLandmark(addrReq.landmark());
                address.setDistrict(addrReq.district());
                address.setState(addrReq.state());
                address.setCountry(addrReq.country());
                address.setPinCode(addrReq.pinCode());
                // Do NOT set userEntity here. Set it in service after user is built.
                addressList.add(address);
            }
        }
        return Customer.builder()
                .name(req.name())
                .contact(req.contact())
                .address(addressList)
                .build();
    }

    public CustomerResponse fromUserEntity(Customer customer){
        if(customer==null)return null;
        return new CustomerResponse(customer.getUserId(),
                customer.getName(), customer.getEmail(), customer.getContact()
        );
    }
}
