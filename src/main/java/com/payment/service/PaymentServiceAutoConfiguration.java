package com.payment.service;

import com.payment.service.filters.JwtFilter;
import com.payment.service.repository.PaymentServiceRepository;
import com.payment.service.service.PaymentService;
import com.payment.service.service.impl.PaymentServiceImpl;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
public class PaymentServiceAutoConfiguration {

    @Bean
    public PaymentService paymentService(PaymentServiceRepository paymentServiceRepository) {
        return new PaymentServiceImpl(paymentServiceRepository);
    }

    @Bean
    public OpenAPI paymentServiceOpenAPI() {
        SecurityScheme bearerAuthScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
                .schemaRequirement("BearerAuth", bearerAuthScheme)
                .info(new Info().title("Payment Processing MVP API")
                        .description("REST API for Payment Processing MVP Assignment")
                        .version("v1.0")
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub Repo")
                        .url("https://github.com/yourusername/payment-service-mvp"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/swagger-ui/**", "/v3/**").permitAll()
                        .requestMatchers("/**.html").permitAll()
                        .requestMatchers("/style.css", "/script.js").permitAll()
                        .requestMatchers("/favicon.ico").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtFilter(),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
