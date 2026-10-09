package com.microservice.customerservice.processor;

import com.microservice.customerservice.dao.CustomerDAO;
import com.microservice.customerservice.dto.processor.CustomerProcessorDTO;
import com.microservice.customerservice.dto.request.CustomerCreateRequest;
import com.microservice.customerservice.dto.request.CustomerUpdateRequest;
import com.microservice.customerservice.dto.response.CustomerResponse;
import com.microservice.customerservice.entity.Customer;
import com.microservice.customerservice.entity.CustomerStatus;
import com.microservice.customerservice.exceptions.CustomerAlreadyExistException;
import com.microservice.customerservice.exceptions.CustomerNotFoundException;
import com.microservice.customerservice.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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


    @Transactional(readOnly = true)
    public CustomerResponse getCustomer(Long id)
    {
        Customer customer = customerDAO.findByID(id).orElseThrow(()-> new CustomerNotFoundException(id));
        return customerMapper.toResponse(customer);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomers()
    {
        return customerDAO.findAll()
                .stream()
                .map(customerMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getCustomerByStatus(CustomerStatus status)
    {
        return customerDAO.findByStatus(status)
                .stream()
                .map(customerMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomerByEmail(String email)
    {
        Customer customer = customerDAO.findByEmail(email).orElseThrow(()->new CustomerNotFoundException("Customer email ID is not found: "+ email));;
        return customerMapper.toResponse(customer);
    }

    @Transactional
    public CustomerResponse updateCustomer(CustomerUpdateRequest request, Long id)
    {
        Customer customer = customerDAO.findByID(id).orElseThrow(() -> new CustomerNotFoundException(id));

        if (request.getFirstName() != null)
        {
            customer.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null)
        {
            customer.setLastName(request.getLastName());
        }
        if (request.getEmail() != null && !request.getEmail().equalsIgnoreCase(customer.getEmail()))
        {
            if(customerDAO.existsByEmail(request.getEmail()))
            {
                throw new CustomerAlreadyExistException("Customer email id is already exist :"+request.getEmail());

            }
            customer.setEmail(request.getEmail());
        }
        if (request.getPhone() != null)
        {
            customer.setPhone(request.getPhone());
        }

        if (request.getStatus() != null)
        {
            customer.setStatus(CustomerStatus.valueOf(request.getStatus().toUpperCase()));
        }

        Customer updateCustomer = customerDAO.save(customer);

        CustomerResponse response = customerMapper.toResponse(updateCustomer);

        producerTemplate.sendBody("direct:customer-update", response);
        return response;
    }


    @Transactional
    public void deleteCustomer(Long id)
    {
        Customer customer = customerDAO.findByID(id).orElseThrow(()->new CustomerNotFoundException(id));
        customer.setStatus(CustomerStatus.DELETED);
        customerDAO.save(customer);
        producerTemplate.sendBody("direct:customer-delete", id);
    }
}
