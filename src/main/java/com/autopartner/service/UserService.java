package com.autopartner.service;

import com.autopartner.controller.DTO.CompanyRegistrationRequest;
import com.autopartner.domain.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

  Iterable<User> listAllUsers();

  User getUserById(Long id);

  User saveUser(User user);

  void deleteUser(Long id);

  boolean isUsernameUnique(User user);

  User getUserByUsername(String username);
}
