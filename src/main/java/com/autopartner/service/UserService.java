package com.autopartner.service;

import com.autopartner.api.dto.request.UserRequest;
import com.autopartner.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

  List<User> findAll(Long companyId);

  Optional<User> findById(Long id, Long companyId);

  User save(User user);

  User create(UserRequest request, Long companyId);

  User update(User user, UserRequest request);

  void delete(User user);

  Optional<Long> findIdByEmail(String email);
}
