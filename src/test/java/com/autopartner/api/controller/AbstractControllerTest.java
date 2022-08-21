package com.autopartner.api.controller;

import com.autopartner.api.auth.JwtVerifier;
import com.autopartner.domain.UserFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class AbstractControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    JwtVerifier tokenService;

    @MockBean
    UserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        String username = "test@username.com";
        when(tokenService.verify(any())).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(UserFixture.createUser());
    }

    protected static MockHttpServletRequestBuilder auth(MockHttpServletRequestBuilder builder) {
        return builder.header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHJha3V0ZW4uY29tIiwiaXNzIjoidGVzdEByYWt1dGVuLmNvbSIsImV4cCI6NzIwMTY0ODA1MzMxMX0.pI4PBB_FvxO2YWw_QOruFYFH_TJ7s6tyMSiYDFHWsZk");
    }

}
