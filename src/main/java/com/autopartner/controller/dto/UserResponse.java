package com.autopartner.controller.dto;

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
  String username;
  String firstName;
  String lastName;
  String authorities;
  Boolean active;
  Long companyId;

  public static UserResponse createUserResponse(User user) {
    return UserResponse.builder()
            .id(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .authorities(user.getAuthorities())
            .active(user.getActive())
            .companyId(user.getCompanyId())
            .build();
  }
}
