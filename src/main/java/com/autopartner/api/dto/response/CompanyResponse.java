package com.autopartner.api.dto.response;

import com.autopartner.domain.Company;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyResponse {

  Long id;
  String name;
  String country;
  String city;

  public static CompanyResponse fromEntity(Company company) {
    return CompanyResponse.builder()
        .id(company.getId())
        .name(company.getName())
        .country(company.getCountry())
        .city(company.getCity())
        .build();
  }

}
