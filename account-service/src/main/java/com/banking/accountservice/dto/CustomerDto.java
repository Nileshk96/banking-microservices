package com.banking.accountservice.dto;

import lombok.Data;

@Data
public class CustomerDto {
    private Long id;
    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}