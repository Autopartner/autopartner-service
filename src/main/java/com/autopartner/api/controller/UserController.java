package com.autopartner.api.controller;

import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

import com.autopartner.api.dto.UserResponse;
import com.autopartner.domain.User;
import com.autopartner.exception.UserAlreadyExistsException;
import com.autopartner.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserController {

  UserService userService;

  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping
  public Iterable<User> getAllUsers() {
    return userService.listAllUsers();
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  @GetMapping("/{id}")
  public UserResponse getUser(@PathVariable Long id) {
    User user = userService.getUserById(id);
    return UserResponse.createUserResponse(user);
  }

  @Secured("ROLE_ADMIN")
  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable Long id) {
    User user = userService.getUserById(id);
    log.info("Deleted user {}", user);
    userService.deleteUser(user.getId());
  }

  @Secured("ROLE_ADMIN")
  @PostMapping
  public UserResponse newUser(@Valid User user) {
    if (nonNull(userService.getUserByEmail(user.getEmail()))) {
      log.error("User already exists with email: {}", user.getEmail());
      throw new UserAlreadyExistsException("User already exists with email: " + user.getEmail());
    }
    userService.saveUser(user);
    log.info("Created new user {}", user);
    return UserResponse.createUserResponse(user);
  }

  @Secured("ROLE_ADMIN")
  @PutMapping
  public UserResponse updateUser(@Valid User user) {
    if (userService.getUserByEmail(user.getEmail()).equals(user)) {
      log.error("User already exists with email: {}", user.getEmail());
      throw new UserAlreadyExistsException("User already exists with email: " + user.getEmail());
    }
    userService.saveUser(user);
    log.info("Updated user {}", user);
    return UserResponse.createUserResponse(user);
  }

}
