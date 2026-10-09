package com.microservice.customerservice.exceptions;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(Long id){
        super("Customer ID is not found" + id);
    }

    public CustomerNotFoundException(String message){
        super(message);
    }
}
