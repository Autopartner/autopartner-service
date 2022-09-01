package com.autopartner.service.impl;

import com.autopartner.api.dto.request.UserRequest;
import com.autopartner.domain.User;
import com.autopartner.repository.UserRepository;
import com.autopartner.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

  UserRepository userRepository;

  PasswordEncoder passwordEncoder;

  @Override
  public List<User> findAll() {
    return userRepository.findAllByActiveTrue();
  }

  @Override
  public Optional<User> findById(Long id) {
    return userRepository.findByIdAndActiveTrue(id);
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

  // TODO refactor
  @Override
  @Transactional
  public boolean isEmailUnique(User user) {
    User u = userRepository.findOneByEmail(user.getEmail());
    return Objects.equals(u.getId(), user.getId());
  }

  @Override
  public Optional<User> findByEmail(String username) {
    return Optional.of(userRepository.findOneByEmail(username));
  }

  @Override
  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

}
