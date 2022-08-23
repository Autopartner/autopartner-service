package com.autopartner.api.dto.request;

import com.autopartner.api.dto.request.CompanyRegistrationRequest;

public class CompanyRegistrationRequestFixture {
  public static CompanyRegistrationRequest createCompanyRegistrationRequestWithoutPassword() {
    return CompanyRegistrationRequest.builder()
            .email("company@gmail.com")
            .name("company")
            .country("country")
            .city("city")
            .firstName("firstName")
            .lastName("lastName")
            .phone("+380111111111")
            .build();
  }

  public static CompanyRegistrationRequest createCompanyRegistrationRequest() {
    return CompanyRegistrationRequest.builder()
        .email("company@gmail.com")
        .name("company")
        .country("country")
        .city("city")
        .firstName("firstName")
        .lastName("lastName")
        .phone("+380111111111")
        .password("12345678")
        .build();
  }
}
