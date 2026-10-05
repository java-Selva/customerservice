package com.microservice.customerservice.mapper;

import com.microservice.customerservice.dto.response.CustomerResponse;
import com.microservice.customerservice.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerResponse toResponse(Customer customer)
    {
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .status(customer.getStatus().name)
                .createdAt(customer.getCreatedAt())
                .updateddAt(customer.getUpdatedAt())
                .build();
    }


}
