package com.autopartner.domain;

public class CarBrandFixture {
  public static CarBrand createCarBrand() {
    return CarBrand.builder()
        .name("Audi")
        .active(true)
        .build();
  }
  public static CarBrand createCarBrandWithDifferentName() {
    return CarBrand.builder()
        .id(2L)
        .name("BMW")
        .active(true)
        .build();
  }
}
