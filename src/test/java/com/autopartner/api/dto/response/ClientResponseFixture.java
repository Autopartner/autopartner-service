package com.autopartner.api.dto.response;

import com.autopartner.api.dto.response.ClientResponse;
import com.autopartner.domain.ClientType;

public class ClientResponseFixture {
    public static ClientResponse createClientResponse(){
        return ClientResponse.builder()
                .id(1L)
                .firstName("Dani")
                .lastName("Kolo")
                .companyName("Imagine")
                .address("Odesa")
                .phone("+380997776655")
                .email("dani@gamail.com")
                .productDiscount(0)
                .taskDiscount(10)
                .clientType(ClientType.PERSON)
                .note("NewOwner")
                .build();
    }
}
