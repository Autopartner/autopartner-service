package com.autopartner.api.dto.response;

import com.autopartner.domain.Car;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Year;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarResponse {
  Long id;
  String vinCode;
  String plateNumber;
  String note;
  Year manufactureYear;
  CarModelResponse carModelResponse;
  ClientResponse clientResponse;

  public static CarResponse fromEntity(Car car) {
    return CarResponse.builder()
        .id(car.getId())
        .vinCode(car.getVinCode())
        .plateNumber(car.getPlateNumber())
        .note(car.getNote())
        .manufactureYear(car.getManufactureYear())
        .carModelResponse(CarModelResponse.fromEntity(car.getCarModel()))
        .clientResponse(ClientResponse.fromEntity(car.getClient()))
        .build();
  }
}
