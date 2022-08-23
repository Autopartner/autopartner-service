package com.autopartner.api.dto.response;

import com.autopartner.api.dto.response.CarTypeResponse;

public class CarTypeResponseFixture {
    public static CarTypeResponse createCarTypeResponse(){
        return CarTypeResponse.builder()
                .id(1L)
                .name("Sedan")
                .build();
    }
}
