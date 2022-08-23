package com.autopartner.api.dto.response;

import com.autopartner.domain.CarType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarTypeResponse {
    Long id;
    String name;

    public static CarTypeResponse fromEntity(CarType carType) {
        return CarTypeResponse.builder()
                .id(carType.getId())
                .name(carType.getName())
                .build();
    }

}
