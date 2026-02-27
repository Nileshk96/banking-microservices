package com.banking.accountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableCaching
public class    AccountServiceApplication {

    public static void main(String[] args) {

        // Force correct timezone before Spring starts
        java.util.TimeZone.setDefault(
                java.util.TimeZone.getTimeZone("Asia/Kolkata")
        );

        System.out.println("JVM Timezone: " + java.util.TimeZone.getDefault());


        SpringApplication.run(AccountServiceApplication.class, args);
    }

}
