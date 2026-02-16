package com.payment.service;

import jakarta.persistence.EntityListeners;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.payment.service.entity")
public class PaymentServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(PaymentServiceApplication.class, args);
    }

}
