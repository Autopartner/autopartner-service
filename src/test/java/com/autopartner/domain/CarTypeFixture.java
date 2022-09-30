package com.autopartner.domain;

public class CarTypeFixture {
  public static CarType createCarType() {
    return CarType.builder()
        .name("Sedan")
        .active(true)
        .build();
  }
  public static CarType createCarTypeWithDifferentName() {
    return CarType.builder()
        .id(1L)
        .name("hatchback")
        .active(true)
        .build();
  }
}