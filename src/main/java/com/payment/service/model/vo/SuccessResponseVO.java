package com.payment.service.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
public class SuccessResponseVO<T> {

    private final boolean success;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Long paymentId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Integer currentPage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Integer totalPages;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Long totalElements;
    private final T data;

    private SuccessResponseVO(String message,
                              Long paymentId,
                              T data, Integer currentPage, Integer totalPages, Long totalElements) {
        this.success = true;
        this.message = message;
        this.paymentId = paymentId;
        this.data = data;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public static <T> SuccessResponseVO<T> of(String message,Long paymentId,
                                               T data){
        return new SuccessResponseVO<>(message, paymentId, data, null, null, null);
    }

    public static <T> SuccessResponseVO<T> of(String message, T data, Integer currentPage, Integer totalPages,
                                              Long totalElements) {
        return new SuccessResponseVO<>(message, null, data, currentPage, totalPages, totalElements);
    }
}
