package com.autopartner.api.controller;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.api.dto.UserRequest;
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

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserController {

  UserService userService;

  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping
  public List<UserResponse> getAllUsers() {
    return userService.findAll().stream()
            .map(UserResponse::createUserResponse)
            .toList();
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  @GetMapping("/{id}")
  public UserResponse getUser(@PathVariable Long id) {
    return userService.findById(id)
            .map(UserResponse::createUserResponse)
            .orElseThrow(() -> new NoSuchElementException("User does not exist: " + id));
  }

  @Secured("ROLE_ADMIN")
  @PostMapping
  public UserResponse create(@Valid @RequestBody UserRequest request) {
    log.info("Received user registration request {}", request);
    String email = request.getEmail();
    if (userService.existsByEmail(email)) {
      log.error("User already exists with email: {}", email);
      throw new UserAlreadyExistsException("User already exists with email: " + email);
    }

    User user = userService.create(request);
    log.info("Created new user {}", request.getEmail());
    return UserResponse.createUserResponse(user);
  }

  @Secured("ROLE_ADMIN")
  @PutMapping("{id}")
  public UserResponse updateUser(@PathVariable Long id, @RequestBody @Valid UserRequest request) {
    User user = userService.findById(id)
            .orElseThrow(() -> new NoSuchElementException("User does not exist"));

    log.info("Updated user {}", user.getEmail());
    return UserResponse.createUserResponse(userService.update(user, request));
  }

  @Secured("ROLE_ADMIN")
  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable Long id) {
    User user = userService.findById(id)
            .orElseThrow(() -> new NoSuchElementException("User does not exist"));
    log.info("Deleted user {}", user.getEmail());
    userService.delete(user);
  }
}
