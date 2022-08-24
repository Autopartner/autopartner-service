package com.autopartner.api.dto.request;

import org.springframework.security.crypto.password.PasswordEncoder;

public class UserRequestFixture {
  public static UserRequest createUser(){
    return UserRequest.builder()
            .email("company@gmail.com")
            .firstName("firstName")
            .lastName("lastName")
            .password("testtt11")
            .phone("+380502222222")
            .build();
  }
}
