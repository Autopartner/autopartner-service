package com.autopartner.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor
public class CompanyRegistrationRequest {

  @Email
  String email;

  @NotEmpty
  String password;

  @NotEmpty
  String companyName;

  @Size(max = 20)
  @NotEmpty
  String country;

  @Size(max = 20)
  @NotEmpty
  String city;

  @NotEmpty
  String firstName;

  @NotEmpty
  String lastName;

  @Pattern(regexp = "\\+38[0-9]{9}")
  String phone;
}