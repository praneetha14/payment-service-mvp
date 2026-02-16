package com.payment.service.model.vo;

import lombok.Getter;

@Getter
public class SuccessResponseVO<T> {

    private final boolean success;
    private final String message;
    private final Long paymentId;
    private final T data;

    private SuccessResponseVO(String message,
                              Long paymentId,
                              T data){
        this.success = true;
        this.message = message;
        this.paymentId = paymentId;
        this.data = data;
    }

    public static <T> SuccessResponseVO<T> of(String message,Long paymentId,
                                               T data){
        return new SuccessResponseVO<>(message, paymentId, data);
    }
}
