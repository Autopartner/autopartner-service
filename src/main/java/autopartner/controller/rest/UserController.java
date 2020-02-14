package autopartner.controller.rest;

import autopartner.domain.entity.User;
import autopartner.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class UserController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Secured({ "ROLE_USER", "ROLE_ADMIN",  "ROLE_SUPER"})
    @RequestMapping(value = {"/user"}, method = RequestMethod.GET)
    public @ResponseBody Iterable<User> getAllUsers() {
        return userService.listAllUsers();
    }

    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public @ResponseBody User getUser(@PathVariable Long id) {
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