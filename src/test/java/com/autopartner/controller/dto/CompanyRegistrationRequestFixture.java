package com.autopartner.controller.dto;

import com.autopartner.controller.dto.CompanyRegistrationRequest;

public class CompanyRegistrationRequestFixture {
  public static CompanyRegistrationRequest createCompanyRegistrationRequest() {
    return CompanyRegistrationRequest.builder()
            .email("company@gmail.com")
            .companyName("company")
            .country("country")
            .city("city")
            .firstName("firstName")
            .lastName("lastName")
            .phone("+380111111111")
            .build();
  }
}
