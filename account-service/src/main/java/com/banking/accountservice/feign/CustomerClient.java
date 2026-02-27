package com.banking.accountservice.feign;

import com.banking.accountservice.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerClient {

    @GetMapping("/customers/by-customer-id/{customerId}")
    CustomerDto getCustomerById(@PathVariable String customerId);
}