package com.autopartner.integration;

import com.autopartner.api.dto.request.ClientRequest;
import com.autopartner.api.dto.request.ClientRequestFixture;
import com.autopartner.api.dto.response.ClientResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientIntegrationTest extends AbstractIntegrationTest {

  @Test
  void create() {
    ClientRequest clientRequest = ClientRequestFixture.createClientRequest();

    ResponseEntity<ClientResponse> response = testRestTemplate.postForEntity("/api/v1/clients", clientRequest, ClientResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getId()).isNotNull();

  }

}
