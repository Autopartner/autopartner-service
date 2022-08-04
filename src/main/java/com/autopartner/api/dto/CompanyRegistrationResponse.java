package com.autopartner.api.dto;

import com.autopartner.domain.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor
@Builder
public class CompanyRegistrationResponse {

  Long id;

  String companyName;

  String country;

  String city;

  public static CompanyRegistrationResponse createResponse(Company company) {
    return CompanyRegistrationResponse.builder()
            .id(company.getId())
            .companyName(company.getCompanyName())
            .country(company.getCountry())
            .city(company.getCity())
            .build();
  }
}
