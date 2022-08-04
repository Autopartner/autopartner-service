package com.autopartner.service;

import com.autopartner.domain.User;

public interface UserService {

  Iterable<User> listAllUsers();

  User getUserById(Long id);

  User saveUser(User user);

  void deleteUser(Long id);

  boolean isUsernameUnique(User user);

  User getUserByEmail(String username);
}
