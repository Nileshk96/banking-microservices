package com.banking.transactionservice.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class Account {

    private Long id;
    private String customerId;
    private String accountType;
    private BigDecimal balance;   // 🔥 MUST match Account Service
}