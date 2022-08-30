package com.autopartner.domain;

import java.time.LocalDate;

public class CarFixture {

  public static Car createCar() {
    return Car.builder()
            .id(2L)
            .plateNumber("BH1111AA")
            .manufactureYear(LocalDate.parse("2020-02-03"))
            .vinCode("Aa1111")
            .active(true)
            .note("New car")
            .build();
  }
}
