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

  @NotEmpty
  @Size(min = 3, max = 256)
  String companyName;

  @NotEmpty
  @Size(min = 3, max = 256)
  String country;

  @NotEmpty
  @Size(min = 3, max = 256)
  String city;

  public static CompanyRegistrationResponse createResponse(CompanyRegistrationRequest request) {
    return CompanyRegistrationResponse.builder()
            .companyName(request.getCompanyName())
            .country(request.getCountry())
            .city(request.getCity())
            .build();
  }
}
