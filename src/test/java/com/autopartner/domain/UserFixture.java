package com.autopartner.domain;

public class UserFixture {

  public static User createUser() {
    return User.builder()
        .id(1L)
        .email("company@gmail.com")
        .firstName("firstName")
        .lastName("lastName")
        .active(true)
        .build();
  }
}
