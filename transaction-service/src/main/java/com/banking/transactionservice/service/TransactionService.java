package com.banking.transactionservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.math.BigDecimal;

import com.banking.transactionservice.repository.TransactionRepository;
import com.banking.transactionservice.entity.Transaction;
import com.banking.transactionservice.feign.AccountClient;
import com.banking.transactionservice.kafka.TransactionProducer;
import com.banking.transactionservice.dto.Account;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;
    private final AccountClient accountClient;
    private final TransactionProducer producer;

    public Transaction processTransaction(Transaction transaction) {

        // 1️⃣ Fetch full account
        Account account = accountClient.getAccount(transaction.getAccountId());

        if (account == null) {
            throw new RuntimeException("Account not found");
        }

        BigDecimal amount = BigDecimal.valueOf(transaction.getAmount());

        // 2️⃣ Apply business logic
        if ("DEBIT".equalsIgnoreCase(transaction.getType())) {

            if (account.getBalance().compareTo(amount) < 0) {
                throw new RuntimeException("Insufficient balance");
            }

            account.setBalance(account.getBalance().subtract(amount));

        } else { // CREDIT

            account.setBalance(account.getBalance().add(amount));
        }

        // 3️⃣ Update account via Feign
        accountClient.updateAccount(account.getId(), account);

        // 4️⃣ Save transaction
        transaction.setTimestamp(LocalDateTime.now());
        Transaction saved = repository.save(transaction);

        // 5️⃣ Publish Kafka event
        producer.publishEvent("Transaction Completed: " + saved.getId());

        return saved;
    }
}