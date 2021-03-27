package com.autopartner.controller.rest;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.domain.User;
import com.autopartner.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserController {

  UserService userService;

  @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"})
  @RequestMapping(value = {"/user"}, method = RequestMethod.GET)
  public @ResponseBody
  Iterable<User> getAllUsers() {
    return userService.listAllUsers();
  }

  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
  public @ResponseBody
  User getUser(@PathVariable Long id) {
    return userService.getUserById(id);
  }

  @Secured("ROLE_ADMIN")
  @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
  public void deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
  }

  @Secured("ROLE_ADMIN")
  @RequestMapping(value = "/user", method = {RequestMethod.POST, RequestMethod.PUT})
  public void newUser(@Valid User user) {
    userService.saveUser(user);
  }

}
