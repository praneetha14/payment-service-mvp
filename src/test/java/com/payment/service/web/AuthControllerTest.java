package com.payment.service.web;

import com.payment.service.controller.AuthController;
import com.payment.service.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@ExtendWith(SpringExtension.class)
public class AuthControllerTest {

    private static final String LOGIN_URL = "/auth/login";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtils jwtUtils;

    @Test
    @WithMockUser
    void loginSuccessTest() throws Exception {

        try (MockedStatic<JwtUtils> mockedStatic = mockStatic(JwtUtils.class)) {
            mockedStatic.when(() -> JwtUtils.generateToken(anyString())).thenReturn("jwt");
        }

        mockMvc.perform(post(LOGIN_URL)
                        .param("username", "admin")
                        .param("password", "admin123")
                        .with(csrf()))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser
    void loginFailureTest() throws Exception {

        try (MockedStatic<JwtUtils> mockedStatic = mockStatic(JwtUtils.class)) {
            mockedStatic.when(() -> JwtUtils.generateToken(anyString())).thenReturn("jwt");
        }

        mockMvc.perform(post(LOGIN_URL)
                        .param("username", "admin")
                        .param("password", "aadmin123")
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }


}

