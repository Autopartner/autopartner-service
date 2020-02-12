package autopartner.service;

import autopartner.domain.entity.User;

public interface UserService {

    Iterable<User> listAllUsers();

    User getUserById(Integer id);

    User saveUser(User user);

    void deleteUser(Integer id);

    boolean isUsernameUnique(User user);

    User getUserByUsername(String username);
}
