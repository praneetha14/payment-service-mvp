package com.payment.service;

import com.payment.service.repository.PaymentServiceRepository;
import com.payment.service.service.PaymentService;
import com.payment.service.service.impl.PaymentServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentServiceAutoConfiguration {

    @Bean
    public PaymentService paymentService(PaymentServiceRepository paymentServiceRepository) {
        return new PaymentServiceImpl(paymentServiceRepository);
    }
}
