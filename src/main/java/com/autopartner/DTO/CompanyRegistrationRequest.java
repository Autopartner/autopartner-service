package com.autopartner.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor
public class CompanyRegistrationRequest {

    String email;
    String password;
    String companyName;
    String country;
    String city;
    String firstName;
    String lastName;
    String phone;
}
