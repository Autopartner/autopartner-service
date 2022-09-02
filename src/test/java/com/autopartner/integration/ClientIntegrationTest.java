package com.autopartner.integration;

import com.autopartner.api.dto.request.ClientRequest;
import com.autopartner.api.dto.request.ClientRequestFixture;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClientIntegrationTest extends AbstractIntegrationTest {

  @Test
  void givenClientRequest_whenCreateClient_thenReturnSavedClient() throws Exception {
    ClientRequest clientRequest = ClientRequestFixture.createClientRequest();

    ResultActions result = mockMvc.perform(post("/api/v1/clients")
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(clientRequest)));

    result
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.firstName",
            is(clientRequest.getFirstName())))
        .andExpect(jsonPath("$.lastName",
            is(clientRequest.getLastName())))
        .andExpect(jsonPath("$.companyName",
            is(clientRequest.getCompanyName())))
        .andExpect(jsonPath("$.address",
            is(clientRequest.getAddress())))
        .andExpect(jsonPath("$.phone",
            is(clientRequest.getPhone())))
        .andExpect(jsonPath("$.email",
            is(clientRequest.getEmail())))
        .andExpect(jsonPath("$.productDiscount",
            is(clientRequest.getProductDiscount())))
        .andExpect(jsonPath("$.taskDiscount",
            is(clientRequest.getTaskDiscount())))
        .andExpect(jsonPath("$.clientType",
            is(clientRequest.getClientType().name())))
        .andExpect(jsonPath("$.note",
            is(clientRequest.getNote())));

  }

}
