package com.autopartner.service;

import com.autopartner.domain.User;

import java.util.Optional;

public interface UserService {

  Iterable<User> listAllUsers();

  User getUserById(Long id);

  User saveUser(User user);

  void deleteUser(Long id);

  boolean isUsernameUnique(User user);

  User getUserByUsername(String username);
}
