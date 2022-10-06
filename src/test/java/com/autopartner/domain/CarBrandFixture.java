package com.autopartner.domain;

public class CarBrandFixture {
  public static CarBrand createCarBrand() {
    return CarBrand.builder()
        .companyId(1L)
        .name("Audi")
        .active(true)
        .build();
  }
  public static CarBrand createCarBrandWithDifferentName() {
    return CarBrand.builder()
        .id(1L)
        .companyId(1L)
        .name("BMW")
        .active(true)
        .build();
  }
}
