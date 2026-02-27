package com.banking.customerservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "customerId", nullable = false, unique = true)
    private String customerId;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
