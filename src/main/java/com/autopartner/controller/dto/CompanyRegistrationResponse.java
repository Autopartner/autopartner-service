package com.autopartner.controller.dto;

import com.autopartner.domain.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
