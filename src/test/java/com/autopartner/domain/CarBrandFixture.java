package com.autopartner.domain;

public class CarBrandFixture {

  public static CarBrand createCarBrand() {
    return CarBrand.builder()
            .id(1L)
            .name("Mercedes")
            .active(true)
            .build();
  }
}
