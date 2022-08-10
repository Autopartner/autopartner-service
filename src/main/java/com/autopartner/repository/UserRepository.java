package com.autopartner.repository;

import com.autopartner.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  User findOneByEmail(String email);
  List<User> findAllByActiveTrue();
  Optional<User> findByIdAndActiveTrue(Long id);
  boolean existsByEmail(String email);
}
