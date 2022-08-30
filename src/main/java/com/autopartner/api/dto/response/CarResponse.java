package com.autopartner.api.dto.response;

import com.autopartner.domain.Car;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarResponse {

  String vinCode;
  String plateNumber;
  String note;
  LocalDate manufactureYear;

  public static CarResponse fromEntity(Car car) {
    return CarResponse.builder()
            .vinCode(car.getVinCode())
            .plateNumber(car.getPlateNumber())
            .note(car.getNote())
            .manufactureYear(car.getManufactureYear())
            .build();

  }
}
