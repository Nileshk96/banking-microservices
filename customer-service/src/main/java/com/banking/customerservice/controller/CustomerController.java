package com.banking.customerservice.controller;

import com.banking.customerservice.entity.Customer;
import com.banking.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public Customer create(@RequestBody Customer customer) {
        return service.save(customer);
    }

    @GetMapping
    public List<Customer> getAll() {
        return service.getAll();
    }

    // Existing API (DB primary key)
    @GetMapping("/{id}")
    public Customer getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // ✅ NEW API (Business ID like CUST101)
    @GetMapping("/by-customer-id/{customerId}")
    public Customer getByCustomerId(@PathVariable String customerId) {
        return service.getByCustomerId(customerId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}