package com.microservice.customerservice.controller;

import com.microservice.customerservice.dto.request.CustomerCreateRequest;
import com.microservice.customerservice.dto.response.CustomerResponse;
import com.microservice.customerservice.processor.CustomerProcessor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Customer API", description = "Customer Management API")
public class CustomerController {

    private final CustomerProcessor customerProcessor;

    @PostMapping
    @Operation(summary = "Create Customer", description = "Create a new Customer")
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerCreateRequest request)
    {
        CustomerResponse response = customerProcessor.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
