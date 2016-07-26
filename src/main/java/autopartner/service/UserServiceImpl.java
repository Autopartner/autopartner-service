package autopartner.service;

import autopartner.domain.User;
import autopartner.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Repository
@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Override
    public Iterable<User> listAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findOne(id);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        User user = getUserById(id);
        if (user != null) {
            user.setIsActive(false);
            saveUser(user);
        }
    }

    // TODO refactor
    @Override
    @Transactional
    public boolean isEmailUnique(User user) {
        List<User> users = userRepository.findByEmail(user.getNumber());
        for(User c: users) {
            return Objects.equals(c.getId(), user.getId());
        }
        return true;
    }
}
