package com.autopartner.domain;

import static lombok.AccessLevel.PRIVATE;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.autopartner.DTO.CompanyRegistrationRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@FieldDefaults(level = PRIVATE)
@Builder
public class User {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
  @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
  Long id;

  @Column(name = "username")
  String username;

  @Column(name = "first_name")
  String firstName;

  @Column(name = "last_name")
  String lastName;

  @Column(name = "password")
  String password;

  @Column(name = "email")
  String email;

  @Column(name = "last_password_reset")
  Date lastPasswordReset;

  @Column(name = "authorities")
  String authorities;

  @Column(name = "active")
  Boolean active;

  @Column(name = "company_id")
  Integer companyId;

  public static User createUser(CompanyRegistrationRequest request) {
    return User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .password(request.getPassword())
            .email(request.getEmail())
            .build();
  }
}
