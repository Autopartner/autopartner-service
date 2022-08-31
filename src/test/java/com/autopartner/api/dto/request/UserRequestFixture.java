package com.autopartner.api.dto.request;

public class UserRequestFixture {
  public static UserRequest createUserRequest(){
    return UserRequest.builder()
            .email("company@gmail.com")
            .firstName("firstName")
            .lastName("lastName")
            .password("testtt11")
            .phone("+380502222222")
            .build();
  }
}
