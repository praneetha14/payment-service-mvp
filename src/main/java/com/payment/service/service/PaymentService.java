package com.payment.service.service;

import com.payment.service.model.dto.PaymentRequestDTO;
import com.payment.service.model.vo.PaymentResponseVO;
import com.payment.service.model.vo.SuccessResponseVO;

public interface PaymentService {
    SuccessResponseVO<PaymentResponseVO> processPayment(PaymentRequestDTO paymentRequestDTO);
}
