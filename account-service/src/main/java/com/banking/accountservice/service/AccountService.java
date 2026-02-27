package com.banking.accountservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import com.banking.accountservice.repository.AccountRepository;
import com.banking.accountservice.entity.Account;
import com.banking.accountservice.feign.CustomerClient;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableCaching
public class AccountService {

    private final AccountRepository repository;
    private final CustomerClient customerClient;

    public Account createAccount(Account account) {

        // Validate customer exists
        customerClient.getCustomerById(account.getCustomerId());

        return repository.save(account);
    }

    public List<Account> getAllAccounts() {
        return repository.findAll();
    }

    @Cacheable(value = "accounts", key = "#id")
    public Account getAccountById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public Account updateAccount(Account account) {
        return repository.save(account);
    }

    @CacheEvict(value = "accounts", key = "#id")
    public void updateBalance(Long id, BigDecimal amount) {

        Account account = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance().add(amount));

        repository.save(account);
    }

    public List<Account> getAccountsByCustomerId(String customerId) {
        return repository.findByCustomerId(customerId);
    }
}