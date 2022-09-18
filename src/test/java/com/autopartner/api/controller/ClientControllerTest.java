package com.autopartner.api.controller;

import com.autopartner.api.dto.request.ClientRequest;
import com.autopartner.api.dto.request.ClientRequestFixture;
import com.autopartner.api.dto.response.ClientResponse;
import com.autopartner.api.dto.response.ErrorResponse;
import com.autopartner.domain.Client;
import com.autopartner.domain.ClientFixture;
import com.autopartner.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import static com.autopartner.api.configuration.WebConfiguration.UNAUTHORIZED_RESPONSE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ClientController.class)
@AutoConfigureMockMvc
public class ClientControllerTest extends AbstractControllerTest {

  public static final String URL = "/api/v1/clients";

  @MockBean
  ClientService clientService;

  @Test
  void getAll_NotAuthorized_ReturnsClientError() throws Exception {
    this.mockMvc.perform(get(URL))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(UNAUTHORIZED_RESPONSE)));
  }

  @Test
  void getAll_Authorized_ReturnsClients() throws Exception {
    Client client = ClientFixture.createPersonClient();
    ClientResponse response = ClientResponse.fromEntity(client);
    List<ClientResponse> responses = List.of(response);
    when(clientService.findAll()).thenReturn(List.of(client));
    this.mockMvc.perform(auth(get(URL)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(responses)));
  }

  @Test
  void get_ValidCompanyId_ReturnsClient() throws Exception {
    Client client = ClientFixture.createPersonClient();
    ClientResponse response = ClientResponse.fromEntity(client);
    long id = 1L;
    when(clientService.findById(id)).thenReturn(Optional.of(client));
    this.mockMvc.perform(auth(get(URL + "/" + id)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void get_InvalidClientId_ReturnsError() throws Exception {
    long id = 1L;
    when(clientService.findById(5L)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "Client with id=1 is not found");
    this.mockMvc.perform(auth(get(URL + "/" + id)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void create_ClientAlreadyExists_ReturnsError() throws Exception {
    ClientRequest request = ClientRequestFixture.createClientRequest();
    when(clientService.findIdByPhone(request.getPhone())).thenReturn(Optional.of(1L));
    ErrorResponse errorResponse = new ErrorResponse(400, 402, "Client with param: +380997776655 already exists");
    this.mockMvc.perform(auth(post(URL))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void create_ValidRequest_CreatesClient() throws Exception {
    Client client = ClientFixture.createPersonClient();
    ClientRequest request = ClientRequestFixture.createClientRequest();
    ClientResponse response = ClientResponse.fromEntity(client);
    when(clientService.findIdByPhone(request.getPhone())).thenReturn(Optional.empty());
    when(clientService.create(request, client.getCompanyId())).thenReturn(client);
    this.mockMvc.perform(auth(post(URL))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void update_InvalidClientId_ReturnsError() throws Exception {
    ClientRequest request = ClientRequestFixture.createClientRequest();
    long id = 1L;
    when(clientService.findById(id)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "Client with id=1 is not found");
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_ClientAlreadyExists_ReturnsError() throws Exception {
    Client client = ClientFixture.createPersonClient();
    ClientRequest request = ClientRequestFixture.createClientRequest();
    long id = 1L;
    when(clientService.findById(id)).thenReturn(Optional.of(client));
    when(clientService.findIdByPhone(request.getPhone())).thenReturn(Optional.of(12L));
    ErrorResponse errorResponse = new ErrorResponse(400, 402, "Client with param: +380997776655 already exists");
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_ValidRequest_UpdatesClient() throws Exception {
    Client client = ClientFixture.createPersonClient();
    ClientRequest request = ClientRequestFixture.createClientRequest();
    ClientResponse response = ClientResponse.fromEntity(client);
    long id = 1L;
    when(clientService.findById(id)).thenReturn(Optional.of(client));
    when(clientService.update(client, request)).thenReturn(client);
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void delete_InvalidClientId_ReturnsError() throws Exception {
    ClientRequest request = ClientRequestFixture.createClientRequest();
    long id = 1L;
    when(clientService.findById(id)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "Client with id=1 is not found");
    this.mockMvc.perform(auth(delete(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void delete_ValidRequest_DeletesClient() throws Exception {
    Client client = ClientFixture.createPersonClient();
    long id = 1L;
    when(clientService.findById(id)).thenReturn(Optional.of(client));
    this.mockMvc.perform(auth(delete(URL + "/" + id)))
        .andExpect(status().is2xxSuccessful());
  }
}
