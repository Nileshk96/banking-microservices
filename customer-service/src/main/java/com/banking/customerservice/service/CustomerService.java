package com.banking.customerservice.service;

import com.banking.customerservice.entity.Customer;
import com.banking.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;

import java.util.List;

@Service
@RequiredArgsConstructor
@Cacheable
@CacheEvict
@CachePut
public class CustomerService {

    private final CustomerRepository repository;

    public Customer save(Customer customer) {
        return repository.save(customer);
    }

    public List<Customer> getAll() {
        return repository.findAll();
    }

    @Cacheable(value = "customers", key = "#id")
    public Customer getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    // ✅ FIXED METHOD (this caused your build error)
    public Customer getByCustomerId(String customerId) {
        return repository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @CacheEvict(value = "customers", key = "#id")
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @CachePut(value = "customers", key = "#customer.id")
    public Customer update(Customer customer) {
        return repository.save(customer);
    }

}