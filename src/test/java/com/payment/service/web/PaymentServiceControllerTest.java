package com.payment.service.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.service.controller.PaymentServiceController;
import com.payment.service.model.dto.PaymentRequestDTO;
import com.payment.service.model.vo.PaymentResponseVO;
import com.payment.service.model.vo.SuccessResponseVO;
import com.payment.service.service.PaymentService;
import lombok.With;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PaymentServiceController.class)
@ExtendWith(SpringExtension.class)
class PaymentServiceControllerTest {

    private static final String BASE_URL = "/api/v1/payments";
    private static final String CREATE_PAYMENT_URL = BASE_URL + "/payment";
    private static final String GET_ALL_PAYMENTS_URL = BASE_URL + "/get";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void processPaymentSuccessTest() throws Exception {

        PaymentRequestDTO requestDTO = createPaymentRequestDTO();
        PaymentResponseVO paymentResponseVO =
                new PaymentResponseVO("John Doe", "john@gmail.com",
                        1500.00f, "SUCCESS");
        SuccessResponseVO<PaymentResponseVO> response =
                SuccessResponseVO.of("Payment processed successfully", 1L, paymentResponseVO);

        when(paymentService.processPayment(any(PaymentRequestDTO.class)))
                .thenReturn(response);
        mockMvc.perform(post(CREATE_PAYMENT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                .with(csrf())
        ).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void processPaymentBadRequestFailureTest() throws Exception {

        when(paymentService.processPayment(any(PaymentRequestDTO.class)))
                .thenThrow(new RuntimeException("Invalid input"));

        mockMvc.perform(post(CREATE_PAYMENT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                .with(csrf())
        ).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void getAllPaymentsSuccessTest() throws Exception {

        List<PaymentResponseVO> payments = List.of(
                new PaymentResponseVO("John", "john@gmail.com", 1000.00f, "SUCCESS"),
                new PaymentResponseVO("Alex", "alex@gmail.com", 2000.00f, "SUCCESS")
        );
        SuccessResponseVO<List<PaymentResponseVO>> response =
                SuccessResponseVO.of("Payments fetched successfully", null, payments);
        when(paymentService.getAllPayments()).thenReturn(response);
        mockMvc.perform(get(GET_ALL_PAYMENTS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getAllPaymentsFailureTest() throws Exception {

        when(paymentService.getAllPayments())
                .thenThrow(new RuntimeException("Something went wrong"));
        mockMvc.perform(get(GET_ALL_PAYMENTS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
        ).andExpect(status().isInternalServerError());
    }

    @Test
    void getAllPaymentsWithoutAuthenticationTest() throws Exception {
        mockMvc.perform(get(GET_ALL_PAYMENTS_URL)
        ).andExpect(status().isUnauthorized());
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
