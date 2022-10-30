package com.autopartner.api.dto.response;

import com.autopartner.domain.CarBrand;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarBrandResponse {
  Long id;
  String name;

  public static CarBrandResponse fromEntity(CarBrand carBrand) {
    return CarBrandResponse.builder()
        .id(carBrand.getId())
        .name(carBrand.getName())
        .build();
  }
}
