package com.autopartner.domain;

public class ClientFixture {
  public static Client createClient() {
    return Client.builder()
        .id(1L)
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
        .active(true)
        .build();
  }
}
