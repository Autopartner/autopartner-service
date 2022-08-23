package com.autopartner.api.dto.response;

import com.autopartner.domain.Client;
import com.autopartner.domain.ClientType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientResponse {
    Long id;
    String firstName;
    String lastName;
    String companyName;
    String address;
    String phone;
    String email;
    int productDiscount;
    int taskDiscount;
    ClientType clientType;
    String note;

    public static ClientResponse fromEntity(Client client) {
        return ClientResponse.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .companyName(client.getCompanyName())
                .address(client.getAddress())
                .phone(client.getPhone())
                .email(client.getEmail())
                .productDiscount(client.getProductDiscount())
                .taskDiscount(client.getProductDiscount())
                .clientType(client.getClientType())
                .note(client.getNote())
                .build();
    }

}
