package com.payment.service.service.impl;

import com.payment.service.entity.PaymentEntity;
import com.payment.service.exception.InvalidInputException;
import com.payment.service.model.dto.PaymentRequestDTO;
import com.payment.service.model.enums.PaymentStatusEnum;
import com.payment.service.model.vo.PaymentResponseVO;
import com.payment.service.model.vo.SuccessResponseVO;
import com.payment.service.repository.PaymentServiceRepository;
import com.payment.service.service.PaymentService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentServiceRepository paymentServiceRepository;

    @Override
    public SuccessResponseVO<PaymentResponseVO> processPayment(PaymentRequestDTO paymentRequestDTO) {
        if(paymentRequestDTO.getAmount() == null||paymentRequestDTO.getAmount() < 1){
            throw new InvalidInputException("Amount must be greater than 0");
        }
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setName(paymentRequestDTO.getName());
        paymentEntity.setEmail(paymentRequestDTO.getEmail());
        paymentEntity.setPhoneNumber(paymentRequestDTO.getPhoneNumber());
        paymentEntity.setAmount(paymentRequestDTO.getAmount());
        paymentEntity.setStatus(PaymentStatusEnum.Success);
        paymentEntity.setCreatedAt(LocalDateTime.now());
        paymentEntity = paymentServiceRepository.save(paymentEntity);
        PaymentResponseVO paymentResponseVO = new PaymentResponseVO(
                paymentEntity.getName(),
                paymentEntity.getEmail(),
                paymentEntity.getAmount(),
                paymentEntity.getStatus().name().toLowerCase()
        );
        return SuccessResponseVO.of("Payment processed successfully", paymentEntity.getPaymentId(),
                paymentResponseVO);
    }

    @Override
    public SuccessResponseVO<List<PaymentResponseVO>> getAllPayments() {
        return null;
    }
}
