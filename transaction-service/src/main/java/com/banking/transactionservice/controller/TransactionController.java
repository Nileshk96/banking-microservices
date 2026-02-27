package com.banking.transactionservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.banking.transactionservice.entity.Transaction;
import com.banking.transactionservice.service.TransactionService;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @PostMapping
    public Transaction create(@RequestBody Transaction transaction) {
        return service.processTransaction(transaction);
    }
}