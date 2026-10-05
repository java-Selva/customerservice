package com.microservice.customerservice.dao;

import com.microservice.customerservice.entity.Customer;
import com.microservice.customerservice.entity.CustomerStatus;
import com.microservice.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerDAOImpl implements CustomerDAO{

    private final CustomerRepository customerRepository;

    public CustomerDAOImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> findByID(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public boolean existByEmail(String email) {
        return customerRepository.existByEmail(email);
    }

    @Override
    public List<Customer> findByStatus(CustomerStatus statue) {
        return customerRepository.findByStatus(statue);
    }

    @Override
    public void delete(Customer customer) {
        customerRepository.delete(customer);

    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
