package com.autopartner.domain;

import java.time.LocalDate;
import java.time.Year;

public class CarFixture {

  public static Car createCar() {
    return Car.builder()
        .companyId(1L)
        .plateNumber("BH1111AA")
        .manufactureYear(Year.parse("2020"))
        .vinCode("Aa1111")
        .active(true)
        .note("New car")
        .client(ClientFixture.createPersonClient())
        .carModel(CarModelFixture.createCarModel())
        .build();
  }

  public static Car createCarWithDifferentWinCode() {
    return Car.builder()
        .id(1L)
        .companyId(1L)
        .plateNumber("BH1111AA")
        .manufactureYear(Year.parse("2020"))
        .vinCode("Bb2222")
        .active(true)
        .note("New car")
        .client(ClientFixture.createPersonClient())
        .carModel(CarModelFixture.createCarModel())
        .build();
  }
}
