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

  @Email(message = "Email is not valid")
  @NotEmpty(message = "Email cannot be empty")
  String email;

  @NotEmpty(message = "Password cannot be empty")
  @Size(min = 6, max = 256)
  String password;

  @NotEmpty(message = "Name cannot be empty")
  @Size(min = 3, max = 256)
  String name;

  @NotEmpty(message = "Country cannot be empty")
  @Size(min = 3, max = 256)
  String country;

  @NotEmpty(message = "City cannot be empty")
  @Size(min = 3, max = 256)
  String city;

  @NotEmpty(message = "First name cannot be empty")
  @Size(min = 3, max = 256)
  String firstName;

  @NotEmpty(message = "Last name cannot be empty")
  @Size(min = 3, max = 256)
  String lastName;

  @NotEmpty(message = "Phone cannot be empty")
  @Pattern(regexp = "\\+38[0-9]{10}")
  String phone;
}