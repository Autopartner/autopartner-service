package com.autopartner.api.dto.request;

import lombok.Builder;

@Builder
public class CarModelRequestFixture {
    public static CarModelRequest createCarModelRequest(){
       return CarModelRequest.builder()
               .name("Q5")
               .carBrandId(1L)
               .carTypeId(1L)
               .build();
    }
}
