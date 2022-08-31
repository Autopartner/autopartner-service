package com.autopartner.domain;

public class CarModelFixture {

  public static CarModel createCarModel() {
    return CarModel.builder()
            .id(1L)
            .name("S-series")
            .active(true)
            .carBrand(CarBrandFixture.createCarBrand())
            .carType(CarTypeFixture.createCarType())
            .build();
  }
}
