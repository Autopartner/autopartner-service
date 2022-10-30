package com.autopartner.api.dto.request;

import lombok.Builder;

@Builder
public class CarBrandRequestFixture {
  public static CarBrandRequest createCarBrandRequest() {
    return CarBrandRequest.builder()
        .name("Audi")
        .build();
  }
}
