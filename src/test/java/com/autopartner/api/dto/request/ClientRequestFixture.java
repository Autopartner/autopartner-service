package com.autopartner.api.dto.request;

import com.autopartner.domain.ClientType;

public class ClientRequestFixture {
  public static ClientRequest createClientRequest() {
    return ClientRequest.builder()
        .firstName("Dani")
        .lastName("Kolo")
        .companyName("Imagine")
        .address("Odesa")
        .phone("+380997776655")
        .email("dani@gamail.com")
        .productDiscount(0)
        .taskDiscount(10)
        .clientType(ClientType.PERSON)
        .note("NewOwner")
        .build();
  }
}
