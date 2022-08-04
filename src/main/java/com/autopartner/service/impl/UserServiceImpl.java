package com.autopartner.service.impl;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.domain.User;
import com.autopartner.exception.NotActiveException;
import com.autopartner.repository.UserRepository;
import com.autopartner.service.UserService;

import java.util.Objects;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

  UserRepository userRepository;

  @Override
  public Iterable<User> listAllUsers() {
    return userRepository.findByActiveTrue();
  }

  @Override
  public User getUserById(Long id) {
    User user = userRepository.findByIdAndActiveTrue(id);
    if (user == null) {
      throw new NotActiveException("User does not active");
    }
    return user;
  }

  @Override
  public User saveUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public void deleteUser(Long id) {
    User user = userRepository.findByIdAndActiveTrue(id);
    if (user != null) {
      user.setActive(false);
      saveUser(user);
    }
  }

  // TODO refactor
  @Override
  @Transactional
  public boolean isUsernameUnique(User user) {
    User u = userRepository.findOneByEmail(user.getEmail());
    return Objects.equals(u.getId(), user.getId());
  }

  @Override
  public User getUserByEmail(String username) {
    return userRepository.findOneByEmail(username);
  }
}
