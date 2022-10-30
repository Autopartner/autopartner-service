package com.autopartner.api.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class AuthenticationRequest {

  @NotBlank
  String email;

  @NotBlank
  String password;

}
