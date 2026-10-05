package com.microservice.customerservice.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreateRequest {
    @NotBlank(message = "First Name is Required")
    @Size(min = 2, max = 100, message = "The First Name must be between 2 to 100 characters")
    private String firstName;
    @NotBlank(message = "Last Name is Required")
    @Size(min = 2, max = 100, message = "The Last Name must be between 2 to 100 characters")
    private String lastName;
    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid Email address")
    private String email;
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone Number only with 10 digits")
    private String phone;
}
