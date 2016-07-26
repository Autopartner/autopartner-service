package autopartner.service;

import autopartner.domain.User;

public interface UserService {

    Iterable<User> listAllUsers();

    User getUserById(Integer id);

    User saveUser(User card);

    void deleteUser(Integer id);

    boolean isEmailUnique(User card);

}
