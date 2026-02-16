package com.payment.service.service.impl;

import com.payment.service.model.dto.PaymentRequestDTO;
import com.payment.service.model.vo.PaymentResponseVO;
import com.payment.service.model.vo.SuccessResponseVO;
import com.payment.service.repository.PaymentServiceRepository;
import com.payment.service.service.PaymentService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentServiceRepository paymentServiceRepository;

    @Override
    public SuccessResponseVO<PaymentResponseVO> processPayment(PaymentRequestDTO paymentRequestDTO) {
        return null;
    }
}
