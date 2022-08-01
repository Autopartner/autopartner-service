package com.autopartner.service;

import com.autopartner.domain.User;

public interface UserService {

  Iterable<User> listAllUsers();

  Iterable<User> getByActiveTrue();

  User getUserById(Long id);

  User saveUser(User user);

  void deleteUser(Long id);

  boolean isUsernameUnique(User user);

  User getUserByUsername(String username);
}
