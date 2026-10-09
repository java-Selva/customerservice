package com.microservice.customerservice.controller;

import com.microservice.customerservice.dto.request.CustomerCreateRequest;
import com.microservice.customerservice.dto.request.CustomerUpdateRequest;
import com.microservice.customerservice.dto.response.CustomerResponse;
import com.microservice.customerservice.entity.CustomerStatus;
import com.microservice.customerservice.processor.CustomerProcessor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

    @GetMapping("/{id}")
    @Operation(summary = "get Customer by id", description = "get Customer details by passing the ID")
    public ResponseEntity<CustomerResponse> getCustomerByID(@Parameter(description = "Customer ID") @PathVariable Long id) {
        return ResponseEntity.ok(customerProcessor.getCustomer(id));
    }

    @GetMapping()
    @Operation(summary = "get Customer", description = "get Customer details")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers()
    {
        return ResponseEntity.ok(customerProcessor.getAllCustomers());
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "get Customer by Statue", description = "get Customer details by Statue")
    public ResponseEntity<List<CustomerResponse>> getCustomersByStatus(@PathVariable CustomerStatus status) {
        return ResponseEntity.ok(customerProcessor.getCustomerByStatus(status));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Customer", description = "Update the Customer by Id")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerUpdateRequest request) {
        return ResponseEntity.ok(customerProcessor.updateCustomer(request,id));
    }

    @GetMapping("/{email}")
    @Operation(summary = "get Customer by Email", description = "get Customer details by passing the Email")
    public ResponseEntity<CustomerResponse> getCustomerByEmail(@Parameter(description = "Customer Email") @PathVariable String email)
    {
        return ResponseEntity.ok(customerProcessor.getCustomerByEmail(email));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Customer", description = "Delete the Customer by Id")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerProcessor.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}