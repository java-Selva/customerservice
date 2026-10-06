package com.microservice.customerservice.repository;

import com.microservice.customerservice.entity.Customer;
import com.microservice.customerservice.entity.CustomerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Customer> findByStatus(CustomerStatus status);
}
