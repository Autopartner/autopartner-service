package com.autopartner.api.dto.request;

public class CompanyRequestFixture {
  public static CompanyRequest createCompanyRequest() {
    return CompanyRequest.builder()
        .name("company")
        .country("country")
        .city("city")
        .build();
  }

}
