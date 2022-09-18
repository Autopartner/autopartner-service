package com.autopartner.domain;

public class ClientFixture {
  public static Client createPersonClient() {
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

  public static Client createCompanyClient() {
    return Client.builder()
        .firstName("Serhii")
        .lastName("Kolo")
        .companyName("AutoPartner")
        .address("Odesa")
        .phone("+380000000000")
        .email("serhii@gamail.com")
        .productDiscount(50)
        .taskDiscount(50)
        .clientType(ClientType.COMPANY)
        .note("Owner")
        .build();
  }
}
