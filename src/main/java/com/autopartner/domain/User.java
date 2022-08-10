package com.autopartner.domain;

import static lombok.AccessLevel.PRIVATE;

import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.autopartner.api.dto.CompanyRegistrationRequest;
import com.autopartner.api.dto.UserRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@FieldDefaults(level = PRIVATE)
@Builder
public class User implements UserDetails {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
  @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
  Long id;

  @Column
  String firstName;

  @Column
  String lastName;

  @Column
  String password;

  @Column(unique = true)
  String email;

  @Column
  Date lastPasswordReset;

  @Column
  @Builder.Default
  String authorities = "ROLE_USER";

  @Column
  @Builder.Default
  Boolean active = true;

  @Column
  Long companyId;

  @Column
  String phone;

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return active;
  }

  @Override
  public boolean isAccountNonLocked() {
    return active;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return active;
  }

  @Override
  public boolean isEnabled() {
    return active;
  }

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
  }

  public static User create(CompanyRegistrationRequest request, String password, Long companyId) {
    return User.builder()
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .phone(request.getPhone())
        .password(password)
        .companyId(companyId)
        .build();
  }

  public static User create(UserRequest request) {
    return User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .phone(request.getPhone())
            .build();
  }

  public void update(UserRequest request) {
    firstName = request.getFirstName();
    lastName = request.getLastName();
    email = request.getEmail();
    phone = request.getPhone();
  }

  public void delete() {
    active = false;
  }
}
