package com.autopartner.domain;

public class CarModelFixture {
  public static CarModel createCarModel() {
    return CarModel.builder()
        .id(1L)
        .companyId(1L)
        .name("Q5")
        .carBrand(CarBrandFixture.createCarBrand())
        .carType(CarTypeFixture.createCarType())
        .active(true)
        .build();
  }

  public static CarModel createCarModel(CarBrand brand, CarType type) {
    return CarModel.builder()
        .id(1L)
        .companyId(1L)
        .name("Q5")
        .carBrand(brand)
        .carType(type)
        .active(true)
        .build();
  }

  public static CarModel createCarModelWithDifferentName(CarBrand brand, CarType type) {
    return CarModel.builder()
        .id(1L)
        .companyId(1L)
        .name("Q6")
        .carBrand(brand)
        .carType(type)
        .active(true)
        .build();
  }
}
