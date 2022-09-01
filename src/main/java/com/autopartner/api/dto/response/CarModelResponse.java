package com.autopartner.api.dto.response;

import com.autopartner.domain.CarModel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarModelResponse {
  Long id;
  String name;
  CarBrandResponse brand;
  CarTypeResponse type;

  public static CarModelResponse fromEntity(CarModel model) {
    return CarModelResponse.builder()
        .id(model.getId())
        .name(model.getName())
        .brand(CarBrandResponse.fromEntity(model.getCarBrand()))
        .type(CarTypeResponse.fromEntity(model.getCarType()))
        .build();
  }
}

