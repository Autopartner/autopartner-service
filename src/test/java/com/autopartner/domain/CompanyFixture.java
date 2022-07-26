package com.autopartner.domain;

public class CompanyFixture {
  public static Company createCompany() {
    return Company.builder()
            .id(1L)
            .companyName("company")
            .country("country")
            .city("city")
            .active(true)
            .build();
  }
}
