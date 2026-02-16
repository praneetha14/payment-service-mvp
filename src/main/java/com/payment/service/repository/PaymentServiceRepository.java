package com.payment.service.repository;

import com.payment.service.entity.PaymentEntity;
import com.payment.service.model.dto.PaymentRequestDTO;
import com.payment.service.model.vo.PaymentResponseVO;
import com.payment.service.model.vo.SuccessResponseVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentServiceRepository extends JpaRepository<PaymentEntity, Long> {

}
