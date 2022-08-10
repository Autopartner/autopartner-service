package com.autopartner.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor
@Builder
public class CompanyRegistrationRequest {

  @Email
  @NotEmpty
  String email;

  @NotEmpty
  @Size(min = 6, max = 256)
  String password;

  @NotEmpty
  @Size(min = 3, max = 256)
  String name;

  @NotEmpty
  @Size(min = 3, max = 256)
  String country;

  @NotEmpty
  @Size(min = 3, max = 256)
  String city;

  @NotEmpty
  @Size(min = 3, max = 256)
  String firstName;

  @NotEmpty
  @Size(min = 3, max = 256)
  String lastName;

  @NotEmpty
  @Pattern(regexp = "\\+38[0-9]{10}")
  String phone;
}