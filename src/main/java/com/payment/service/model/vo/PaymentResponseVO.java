package com.payment.service.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentResponseVO {

    private final Long paymentId;
    private final String name;
    private final String email;
    private final Float amount;
    private final String status;
}
