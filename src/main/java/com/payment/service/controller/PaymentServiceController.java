package com.payment.service.controller;

import com.payment.service.model.dto.PaymentRequestDTO;
import com.payment.service.model.vo.PaymentResponseVO;
import com.payment.service.model.vo.SuccessResponseVO;
import com.payment.service.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentServiceController {
    private final PaymentService paymentService;

    @PostMapping("/payment")
    public ResponseEntity<SuccessResponseVO<PaymentResponseVO>> processPayment(
            @Valid
            @RequestBody
            PaymentRequestDTO paymentRequestDTO) {
        return new ResponseEntity<>(paymentService.processPayment(paymentRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<SuccessResponseVO<List<PaymentResponseVO>>> getAllPayment(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit){
        return ResponseEntity.ok(paymentService.getAllPayments(page, limit));
    }
}
