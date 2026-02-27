package com.banking.customerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
public class CustomerServiceApplication {

    public static void main(String[] args) {

        // Force correct timezone before Spring starts
     /*  java.util.TimeZone.setDefault(
                java.util.TimeZone.getTimeZone("Asia/Kolkata")
        );

        System.out.println("JVM Timezone: " + java.util.TimeZone.getDefault()); */
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("UTC"));

        SpringApplication.run(CustomerServiceApplication.class, args);
    }

}
