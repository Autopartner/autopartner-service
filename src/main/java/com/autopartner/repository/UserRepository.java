package com.autopartner.repository;

import com.autopartner.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  User findOneByEmail(String email);
  Iterable<User> findByActiveTrue();
  User findByIdAndActiveTrue(Long id);
}
