package com.autopartner.repository;

import com.autopartner.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
  User findOneByUsername(String username);
  Iterable<User> findByActiveTrue();
  User findByIdAndActiveTrue(Long id);
}
