package com.microservice.customerservice.dao;

import com.microservice.customerservice.entity.Customer;
import com.microservice.customerservice.entity.CustomerStatus;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    Customer save(Customer customer);
    Optional<Customer> findByID(Long id);
    Optional<Customer> findByEmail(String email);
    boolean existByEmail(String email);
    List<Customer> findByStatus(CustomerStatus statue);
    void delete(Customer customer);
    List<Customer> findAll();
}
