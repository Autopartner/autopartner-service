package com.autopartner.controller.rest;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.controller.dto.UserResponse;
import com.autopartner.domain.User;
import com.autopartner.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
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
    userService.deleteUser(id);
  }

  @Secured("ROLE_ADMIN")
  @PostMapping
  public UserResponse newUser(@Valid User user) {
    userService.saveUser(user);
    return UserResponse.createUserResponse(user);
  }

  @Secured("ROLE_ADMIN")
  @PutMapping
  public UserResponse updateUser(@Valid User user) {
    userService.updateUser(user);
    return UserResponse.createUserResponse(user);
  }

}
