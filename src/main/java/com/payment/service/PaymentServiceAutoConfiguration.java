package com.payment.service;

import com.payment.service.repository.PaymentServiceRepository;
import com.payment.service.service.PaymentService;
import com.payment.service.service.impl.PaymentServiceImpl;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentServiceAutoConfiguration {

    @Bean
    public PaymentService paymentService(PaymentServiceRepository paymentServiceRepository) {
        return new PaymentServiceImpl(paymentServiceRepository);
    }

    @Bean
    public OpenAPI paymentServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Payment Processing MVP API")
                        .description("REST API for Payment Processing MVP Assignment")
                        .version("v1.0")
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub Repo")
                        .url("https://github.com/yourusername/payment-service-mvp"));
    }
}
