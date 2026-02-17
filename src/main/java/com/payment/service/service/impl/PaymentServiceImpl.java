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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentServiceRepository paymentServiceRepository;

    @Override
    public SuccessResponseVO<PaymentResponseVO> processPayment(PaymentRequestDTO paymentRequestDTO) {
        if(!paymentRequestDTO.getName().matches("^[A-Za-z ]{3,50}$")) {
            throw new InvalidInputException("Name must be 3-50 characters, alphabets and spaces only");
        }

        if(!paymentRequestDTO.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            throw new InvalidInputException("Invalid email format");
        }

        if(!paymentRequestDTO.getPhoneNumber().matches("^[0-9]{10}$")) {
            throw new InvalidInputException("Phone number must be exactly 10 digits");
        }

        if(paymentRequestDTO.getAmount() == null||paymentRequestDTO.getAmount() < 1||paymentRequestDTO.getAmount() > 100000) {
            throw new InvalidInputException("Amount must be between than 1.00 and 100000.00");
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
    public SuccessResponseVO<List<PaymentResponseVO>> getAllPayments(int page, int limit) {
        if (page < 0) {
            page = 0;
        }
        if (limit < 0) {
            limit = 10;
        }
        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<PaymentEntity> paymentEntities = paymentServiceRepository.findAll(pageRequest);
        List<PaymentEntity> finalPaymentEntities;
        if (paymentEntities.hasContent()) {
            finalPaymentEntities = paymentEntities.getContent();
        } else {
            return SuccessResponseVO.of( "Payments retrieved successfully", null, List.of());
        }
        List<PaymentResponseVO> paymentResponseVOList = new ArrayList<>();
        for (PaymentEntity entity : finalPaymentEntities) {
            PaymentResponseVO paymentResponseVO = new PaymentResponseVO(
                    entity.getName(),
                    entity.getEmail(),
                    entity.getAmount(),
                    entity.getStatus().name().toLowerCase()
            );
            paymentResponseVOList.add(paymentResponseVO);
        }
        return SuccessResponseVO.of( "Payments retrieved successfully", paymentResponseVOList,
                paymentEntities.getNumber(), paymentEntities.getTotalPages(), paymentEntities.getTotalElements());
    }
}
