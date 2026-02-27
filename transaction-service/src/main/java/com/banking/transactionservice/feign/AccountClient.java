package com.banking.transactionservice.feign;

import com.banking.transactionservice.dto.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ACCOUNT-SERVICE")
public interface AccountClient {

    @GetMapping("/accounts/{id}")
    Account getAccount(@PathVariable("id") Long id);

    @PutMapping("/accounts/{id}")
    Account updateAccount(@PathVariable("id") Long id,
                          @RequestBody Account account);
}