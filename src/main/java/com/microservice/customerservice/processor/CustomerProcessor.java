package com.microservice.customerservice.processor;

import com.microservice.customerservice.dao.CustomerDAO;
import com.microservice.customerservice.dto.processor.CustomerProcessorDTO;
import com.microservice.customerservice.dto.request.CustomerCreateRequest;
import com.microservice.customerservice.dto.response.CustomerResponse;
import com.microservice.customerservice.entity.Customer;
import com.microservice.customerservice.entity.CustomerStatus;
import com.microservice.customerservice.exceptions.CustomerAlreadyExistException;
import com.microservice.customerservice.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerProcessor {

    private final CustomerDAO  customerDAO;
    private final CustomerMapper customerMapper;
    private final ProducerTemplate  producerTemplate;

    @Transactional
    public CustomerResponse createCustomer (CustomerCreateRequest request)
    {
        CustomerProcessorDTO processorDTO = CustomerProcessorDTO.builder()
                .customerCreateRequest(request)
                .correlationID(UUID.randomUUID().toString())
                .build();

        if(customerDAO.existsByEmail(request.getEmail()))
        {
            throw new CustomerAlreadyExistException("Customer already exist with the same email" + request.getEmail());
        }

        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .status(CustomerStatus.ACTIVE)
                .build();

        Customer savedCustomer = customerDAO.save(customer);
        processorDTO.setCustomer(savedCustomer);
        CustomerResponse response = customerMapper.toResponse(savedCustomer);
        processorDTO.setResponse(response);
        producerTemplate.sendBody("direct:customer-created", response);
        return response;
    }

}
