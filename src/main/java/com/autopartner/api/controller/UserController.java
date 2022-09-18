package com.autopartner.api.controller;

import com.autopartner.api.dto.request.UserRequest;
import com.autopartner.api.dto.response.UserResponse;
import com.autopartner.domain.User;
import com.autopartner.exception.AlreadyExistsException;
import com.autopartner.exception.NotFoundException;
import com.autopartner.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserController {

  UserService userService;

  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @GetMapping
  public List<UserResponse> getAll() {
    return userService.findAll().stream()
        .map(UserResponse::fromEntity)
        .toList();
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  @GetMapping("/{id}")
  public UserResponse get(@PathVariable Long id) {
    return userService.findById(id)
        .map(UserResponse::fromEntity)
        .orElseThrow(() -> new NotFoundException("User", id));
  }

  @Secured("ROLE_ADMIN")
  @PostMapping
  public UserResponse create(@Valid @RequestBody UserRequest request,
                             @AuthenticationPrincipal User user) {
    log.info("Received user registration request {}", request);
    String email = request.getEmail();
    if (userService.findIdByEmail(email).isPresent()) {
      throw new AlreadyExistsException("User", email);
    }
    User newUser = userService.create(request, user.getCompanyId());
    log.info("Created new user {}", request.getEmail());
    return UserResponse.fromEntity(newUser);
  }

  @Secured("ROLE_ADMIN")
  @PutMapping("{id}")
  public UserResponse update(@PathVariable Long id, @RequestBody @Valid UserRequest request) {
    User user = userService.findById(id)
        .orElseThrow(() -> new NotFoundException("User", id));
    Optional<Long> foundId = userService.findIdByEmail(request.getEmail());
    if (foundId.isPresent() && !foundId.get().equals(id)) {
      throw new AlreadyExistsException("User", request.getEmail());
    }
    log.info("Updated user {}", user.getEmail());
    return UserResponse.fromEntity(userService.update(user, request));
  }

  @Secured("ROLE_ADMIN")
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    User user = userService.findById(id)
        .orElseThrow(() -> new NotFoundException("User", id));
    log.info("Deleted user {}", user.getEmail());
    userService.delete(user);
  }
}
