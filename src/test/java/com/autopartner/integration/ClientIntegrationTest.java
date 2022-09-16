package com.autopartner.integration;

import com.autopartner.api.dto.request.ClientRequest;
import com.autopartner.api.dto.request.ClientRequestFixture;
import com.autopartner.domain.Client;
import com.autopartner.domain.ClientFixture;
import com.autopartner.domain.ClientType;
import com.autopartner.repository.ClientRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientIntegrationTest extends AbstractIntegrationTest {

  public static final String CLIENTS_URL = "/api/v1/clients";
  @Autowired
  ClientRepository clientRepository;

  @Override
  @BeforeEach
  void setUp() throws Exception {
    super.setUp();
    clientRepository.deleteAll();
  }

  @Test
  void givenClientRequest_whenCreateClient_thenReturnSavedClientResponse() throws Exception {
    ClientRequest clientRequest = ClientRequestFixture.createClientRequest();

    ResultActions result = mockMvc.perform(post(CLIENTS_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(clientRequest)));

    result
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName", is(clientRequest.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(clientRequest.getLastName())))
        .andExpect(jsonPath("$.companyName", is(clientRequest.getCompanyName())))
        .andExpect(jsonPath("$.address", is(clientRequest.getAddress())))
        .andExpect(jsonPath("$.phone", is(clientRequest.getPhone())))
        .andExpect(jsonPath("$.email", is(clientRequest.getEmail())))
        .andExpect(jsonPath("$.productDiscount", is(clientRequest.getProductDiscount())))
        .andExpect(jsonPath("$.taskDiscount", is(clientRequest.getTaskDiscount())))
        .andExpect(jsonPath("$.clientType", is(clientRequest.getClientType().name())))
        .andExpect(jsonPath("$.note", is(clientRequest.getNote())));
  }

  @Test
  void givenDuplicatedClientRequest_whenCreateClient_thenReturnAlreadyExistsError() throws Exception {

    clientRepository.save(ClientFixture.createPersonClient());
    ClientRequest clientRequest = ClientRequestFixture.createClientRequest();

    ResultActions result = mockMvc.perform(post(CLIENTS_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(clientRequest)));

    result
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", is("Client with param: +380997776655 already exists")));
  }

  @Test
  public void givenListOfClients_whenGetAllClients_thenReturnClients() throws Exception{

    List<Client> clients = new ArrayList<>();
    clients.add(ClientFixture.createPersonClient());
    clients.add(ClientFixture.createCompanyClient());
    clientRepository.saveAll(clients);

    ResultActions response = mockMvc.perform(get(CLIENTS_URL)
        .header(HttpHeaders.AUTHORIZATION, token));

    response.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.size()",
            is(clients.size())));
  }

  @Test
  public void givenClientId_whenGetClientById_thenReturnClientResponse() throws Exception{

    Client client = clientRepository.save(ClientFixture.createPersonClient());

    ResultActions response = mockMvc.perform(get(CLIENTS_URL + "/{id}", client.getId())
        .header(HttpHeaders.AUTHORIZATION, token));

    response
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName", is(client.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(client.getLastName())))
        .andExpect(jsonPath("$.companyName", is(client.getCompanyName())))
        .andExpect(jsonPath("$.address", is(client.getAddress())))
        .andExpect(jsonPath("$.phone", is(client.getPhone())))
        .andExpect(jsonPath("$.email", is(client.getEmail())))
        .andExpect(jsonPath("$.productDiscount", is(client.getProductDiscount())))
        .andExpect(jsonPath("$.taskDiscount", is(client.getTaskDiscount())))
        .andExpect(jsonPath("$.clientType", is(client.getClientType().name())))
        .andExpect(jsonPath("$.note", is(client.getNote())));
  }

  @Test
  public void givenUpdatedClientRequest_whenUpdateClient_thenReturnUpdateClientResponse() throws Exception{

    Client client = ClientFixture.createCompanyClient();
    clientRepository.save(client);

    ClientRequest clientRequest = ClientRequest.builder()
        .firstName("New")
        .lastName("Name")
        .companyName("New company")
        .address("New city")
        .phone("+380000000001")
        .email("new@email.com")
        .productDiscount(70)
        .taskDiscount(70)
        .clientType(ClientType.PERSON)
        .note("Another note")
        .build();

    ResultActions response = mockMvc.perform(put(CLIENTS_URL + "/{id}", client.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(clientRequest)));

    response
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName", is(clientRequest.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(clientRequest.getLastName())))
        .andExpect(jsonPath("$.companyName", is(clientRequest.getCompanyName())))
        .andExpect(jsonPath("$.address", is(clientRequest.getAddress())))
        .andExpect(jsonPath("$.phone", is(clientRequest.getPhone())))
        .andExpect(jsonPath("$.email", is(clientRequest.getEmail())))
        .andExpect(jsonPath("$.productDiscount", is(clientRequest.getProductDiscount())))
        .andExpect(jsonPath("$.taskDiscount", is(clientRequest.getTaskDiscount())))
        .andExpect(jsonPath("$.clientType", is(clientRequest.getClientType().name())))
        .andExpect(jsonPath("$.note", is(clientRequest.getNote())));
  }

  @Test
  void givenClientId_whenDeleteClient_thenReturn200() throws Exception {

    Client client = clientRepository.save(ClientFixture.createCompanyClient());

    ResultActions result = mockMvc.perform(delete(CLIENTS_URL + "/{id}", client.getId())
        .header(HttpHeaders.AUTHORIZATION, token));

    result
        .andDo(print())
        .andExpect(status().isOk());
  }

  // TODO: NotFound cases, not unique update

}
