package com.autopartner.api.dto.request;

public class AuthenticationRequestFixture {

  public static AuthenticationRequest createAuthRequest() {
    return AuthenticationRequest.builder()
        .email("company@gmail.com")
        .password("12345678")
        .build();
  }
}
