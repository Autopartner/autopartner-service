package com.autopartner.api.dto;

public class CompanyRequestFixture {
  public static CompanyRequest createCompanyRequest() {
    return CompanyRequest.builder()
            .name("company")
            .country("country")
            .city("city")
            .build();
  }

}
