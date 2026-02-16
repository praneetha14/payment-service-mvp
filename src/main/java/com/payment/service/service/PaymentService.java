package com.payment.service.service;

import com.payment.service.model.dto.PaymentRequestDTO;
import com.payment.service.model.vo.PaymentResponseVO;
import com.payment.service.model.vo.SuccessResponseVO;

import java.util.List;

public interface PaymentService {
    SuccessResponseVO<PaymentResponseVO> processPayment(PaymentRequestDTO paymentRequestDTO);
    SuccessResponseVO<List<PaymentResponseVO>> getAllPayments();

}
