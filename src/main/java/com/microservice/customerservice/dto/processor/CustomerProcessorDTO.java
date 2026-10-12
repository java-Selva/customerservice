package com.microservice.customerservice.dto.processor;

import com.microservice.customerservice.dto.request.CustomerCreateRequest;
import com.microservice.customerservice.dto.request.CustomerUpdateRequest;
import com.microservice.customerservice.dto.response.CustomerResponse;
import com.microservice.customerservice.entity.Customer;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProcessorDTO {
    private CustomerCreateRequest customerCreateRequest;
    private CustomerUpdateRequest customerUpdateRequest;
    private Customer customer;
    private CustomerResponse response;
    private Long customerId;
    private String correlationID;

}
