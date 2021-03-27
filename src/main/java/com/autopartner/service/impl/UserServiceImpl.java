package com.autopartner.service.impl;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.domain.User;
import com.autopartner.repository.UserRepository;
import com.autopartner.service.UserService;
import java.util.Objects;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Transactional
@Service("userService")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

  UserRepository userRepository;

  @Override
  public Iterable<User> listAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public User getUserById(Long id) {
    return userRepository.findById(id).get();
  }

  @Override
  public User saveUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public void deleteUser(Long id) {
    User user = getUserById(id);
    if (user != null) {
      user.setActive(false);
      saveUser(user);
    }
  }

  // TODO refactor
  @Override
  @Transactional
  public boolean isUsernameUnique(User user) {
    User u = userRepository.findOneByUsername(user.getUsername());
    return Objects.equals(u.getId(), user.getId());
  }

  @Override
  public User getUserByUsername(String username) {
    return userRepository.findOneByUsername(username);
  }
}
