package com.autopartner.service.impl;

import com.autopartner.api.dto.request.UserRequest;
import com.autopartner.domain.User;
import com.autopartner.repository.UserRepository;
import com.autopartner.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

  UserRepository userRepository;

  PasswordEncoder passwordEncoder;

  @Override
  public List<User> findAll(Long companyId) {
    return userRepository.findAll(companyId);
  }

  @Override
  public Optional<User> findById(Long id, Long companyId) {
    return userRepository.findById(id, companyId);
  }

  @Override
  public User save(User user) {
    return userRepository.save(user);
  }

  @Override
  public User create(UserRequest request, Long companyId) {
    return save(User.create(request, passwordEncoder.encode(request.getPassword()), companyId));
  }

  @Override
  public User update(User user, UserRequest request) {
    user.update(request);
    return save(user);
  }

  @Override
  public void delete(User user) {
    user.delete();
    save(user);
  }

  @Override
  public Optional<Long> findIdByEmail(String email) {
    return userRepository.findIdByEmail(email);
  }

}
