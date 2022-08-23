package com.autopartner.service.impl;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.api.dto.request.UserRequest;
import com.autopartner.domain.User;
import com.autopartner.repository.UserRepository;
import com.autopartner.service.UserService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

  // TODO add company id
  @Override
  public User create(UserRequest request) {
    return save(User.create(request, passwordEncoder.encode(request.getPassword())));
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
