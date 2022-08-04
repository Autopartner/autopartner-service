package com.autopartner.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthenticationRequest {

  @NotBlank
  String email;

  @NotBlank
  String password;

}
