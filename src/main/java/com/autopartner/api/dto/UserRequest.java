package com.autopartner.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class UserRequest {

  @Email
  @NotEmpty
  String email;

  @NotEmpty
  @Size(min = 3, max = 256)
  String firstName;

  @NotEmpty
  @Size(min = 3, max = 256)
  String lastName;

  @NotEmpty
  @Size(min = 6, max = 256)
  String password;

  @NotEmpty
  @Pattern(regexp = "\\+38[0-9]{10}")
  String phone;
}
