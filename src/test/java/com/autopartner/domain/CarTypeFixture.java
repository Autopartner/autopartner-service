package com.autopartner.domain;

public class CarTypeFixture {
    public static CarType createCarType() {
        return CarType.builder()
                .id(1L)
                .name("Sedan")
                .active(true)
                .build();
    }
}