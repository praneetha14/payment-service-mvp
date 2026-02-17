package com.payment.service.serviceImpl;

import com.payment.service.AbstractTest;
import com.payment.service.exception.InvalidInputException;
import com.payment.service.model.dto.PaymentRequestDTO;
import com.payment.service.model.vo.PaymentResponseVO;
import com.payment.service.model.vo.SuccessResponseVO;
import com.payment.service.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentServiceTest extends AbstractTest {

    private static final String PAYMENT_SUCCESS_MESSAGE = "Payment processed successfully";
    private static final String INVALID_NAME_MESSAGE = "Name must be 3-50 characters, alphabets and spaces only";
    private static final String INVALID_MOBILE_MESSAGE = "Phone number must be exactly 10 digits";
    private static final String INVALID_EMAIL_MESSAGE = "Invalid email format";
    private static final String INVALID_AMOUNT_MESSAGE = "Amount must be between than 1.00 and 100000.00";
    @Autowired
    private PaymentService paymentService;

    @Test
    void processPaymentSuccessTest() {
        PaymentRequestDTO paymentRequestDTO = createPaymentRequestDTO();

        SuccessResponseVO<PaymentResponseVO> response = paymentService.processPayment(paymentRequestDTO);

        assertNotNull(response, ASSERTION_ERROR_MESSAGE);
        assertEquals(PAYMENT_SUCCESS_MESSAGE, response.getMessage(), ASSERTION_ERROR_MESSAGE);
        assertNotNull(response.getData(), ASSERTION_ERROR_MESSAGE);
    }

    @Test
    void processPaymentWithInvalidNameFailureTest() {
        PaymentRequestDTO paymentRequestDTO = createPaymentRequestDTO();
        paymentRequestDTO.setName("Sa");

        Throwable exception = assertThrows(InvalidInputException.class,
                () -> paymentService.processPayment(paymentRequestDTO));

        assertNotNull(exception, ASSERTION_ERROR_MESSAGE);
        assertEquals(INVALID_NAME_MESSAGE, exception.getMessage(), ASSERTION_ERROR_MESSAGE);
    }

    @Test
    void processPaymentWithInvalidMobileFailureTest() {
        PaymentRequestDTO paymentRequestDTO = createPaymentRequestDTO();
        paymentRequestDTO.setPhoneNumber("1234");

        Throwable exception = assertThrows(InvalidInputException.class,
                () -> paymentService.processPayment(paymentRequestDTO));
        assertNotNull(exception, ASSERTION_ERROR_MESSAGE);
        assertEquals(INVALID_MOBILE_MESSAGE, exception.getMessage(), ASSERTION_ERROR_MESSAGE);
    }

    @Test
    void processPaymentWithInvalidEmailFailureTest() {
        PaymentRequestDTO paymentRequestDTO = createPaymentRequestDTO();
        paymentRequestDTO.setEmail("email");

        Throwable exception = assertThrows(InvalidInputException.class,
                () -> paymentService.processPayment(paymentRequestDTO));

        assertNotNull(exception, ASSERTION_ERROR_MESSAGE);
        assertEquals(INVALID_EMAIL_MESSAGE, exception.getMessage(), ASSERTION_ERROR_MESSAGE);
    }

    @Test
    void processPaymentWithInvalidAmountFailureTest() {
        PaymentRequestDTO paymentRequestDTO = createPaymentRequestDTO();
        paymentRequestDTO.setAmount(0.0f);

        Throwable exception = assertThrows(InvalidInputException.class,
                () -> paymentService.processPayment(paymentRequestDTO));

        assertNotNull(exception, ASSERTION_ERROR_MESSAGE);
        assertEquals(INVALID_AMOUNT_MESSAGE, exception.getMessage(), ASSERTION_ERROR_MESSAGE);
    }

    @Test
    void processPaymentWithNullAmountFailureTest() {
        PaymentRequestDTO paymentRequestDTO = createPaymentRequestDTO();
        paymentRequestDTO.setAmount(null);

        Throwable exception = assertThrows(InvalidInputException.class,
                () -> paymentService.processPayment(paymentRequestDTO));

        assertNotNull(exception, ASSERTION_ERROR_MESSAGE);
        assertEquals(INVALID_AMOUNT_MESSAGE, exception.getMessage(), ASSERTION_ERROR_MESSAGE);
    }

    @Test
    void processPaymentWithAmountOutOfBoundFailureTest() {
        PaymentRequestDTO paymentRequestDTO = createPaymentRequestDTO();
        paymentRequestDTO.setAmount(100001.0f);

        Throwable exception = assertThrows(InvalidInputException.class,
                () -> paymentService.processPayment(paymentRequestDTO));

        assertNotNull(exception, ASSERTION_ERROR_MESSAGE);
        assertEquals(INVALID_AMOUNT_MESSAGE, exception.getMessage(), ASSERTION_ERROR_MESSAGE);
    }

    @Test
    void getAllPaymentsSuccessTest() {
        PaymentRequestDTO paymentRequestDTO = createPaymentRequestDTO();
        paymentService.processPayment(paymentRequestDTO);
        SuccessResponseVO<List<PaymentResponseVO>> payments = paymentService
                .getAllPayments(0, 10);

        assertNotNull(payments, ASSERTION_ERROR_MESSAGE);
        assertEquals(1, payments.getData().size(), ASSERTION_ERROR_MESSAGE);
    }

    @Test
    void getAllPaymentsWithNegativePageAndLimitSuccessTest() {
        SuccessResponseVO<List<PaymentResponseVO>> responseVO = paymentService
                .getAllPayments(-1, -10);

        assertNotNull(responseVO, ASSERTION_ERROR_MESSAGE);
        assertTrue(responseVO.getData().isEmpty(), ASSERTION_ERROR_MESSAGE);
    }

    private PaymentRequestDTO createPaymentRequestDTO() {
        PaymentRequestDTO dto = new PaymentRequestDTO();
        dto.setName("John Doe");
        dto.setEmail("john@gmail.com");
        dto.setPhoneNumber("9876543210");
        dto.setAmount(1500.00f);
        return dto;
    }
}
