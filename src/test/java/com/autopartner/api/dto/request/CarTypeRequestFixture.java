package com.autopartner.api.dto.request;

public class CarTypeRequestFixture {
    public static CarTypeRequest createCarTypeRequest(){
        return CarTypeRequest.builder()
                .name("Sedan")
                .build();
    }
}
