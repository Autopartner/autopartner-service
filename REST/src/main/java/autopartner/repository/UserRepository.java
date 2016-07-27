package autopartner.repository;

import autopartner.domain.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findOneByUsername(String username);
}
