package com.autopartner.service;

import com.autopartner.api.dto.UserRequest;
import com.autopartner.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

  List<User> findAll();

  Optional<User> findById(Long id);

  User save(User user);

  User create(UserRequest request);

  User update(User user, UserRequest request);

  void delete(User user);

  boolean isEmailUnique(User user);

  Optional<User> findByEmail(String username);

  boolean existsByEmail(String email);
}
