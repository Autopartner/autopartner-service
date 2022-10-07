package com.autopartner.api.dto.request;

import java.time.Year;

public class CarRequestFixture {

  public static CarRequest createCarRequest() {
    return CarRequest.builder()
        .plateNumber("BH1111AA")
        .manufactureYear(Year.of(2020))
        .vinCode("Aa1111")
        .note("New car")
        .clientId(1L)
        .carModelId(1L)
        .build();
  }
}
