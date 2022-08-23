package com.autopartner.api.dto.request;

import com.autopartner.api.dto.request.CarTypeRequest;

public class CarTypeRequestFixture {
    public static CarTypeRequest createCarTypeRequest(){
        return CarTypeRequest.builder()
                .name("Sedan")
                .build();
    }
}
