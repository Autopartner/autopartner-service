package com.autopartner.api.dto.response;

import com.autopartner.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor
@Builder
public class UserResponse {

  Long id;
  String email;
  String phone;
  String firstName;
  String lastName;
  String authorities;
  Boolean active;
  Long companyId;

  public static UserResponse fromEntity(User user) {
    return UserResponse.builder()
        .id(user.getId())
        .email(user.getEmail())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .phone(user.getPhone())
        .authorities(user.getAuthoritiesString())
        .build();
  }
}
