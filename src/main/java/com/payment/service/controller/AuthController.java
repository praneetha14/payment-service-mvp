package com.payment.service.controller;

import com.payment.service.utils.JwtUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public Map<String, String> login(
            @RequestParam String username,
            @RequestParam String password) {

        if (!"admin".equals(username) || !"admin123".equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = JwtUtils.generateToken(username);

        return Map.of("token", token);
    }
}

