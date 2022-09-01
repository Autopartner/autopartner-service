package com.autopartner.domain;

import lombok.Builder;

@Builder
public class CarModelFixture {
  public static CarModel createCarModel() {
    return CarModel.builder()
        .id(1L)
        .name("Q5")
        .carBrand(CarBrandFixture.createCarBrand())
        .carType(CarTypeFixture.createCarType())
        .active(true)
        .build();
  }
}
