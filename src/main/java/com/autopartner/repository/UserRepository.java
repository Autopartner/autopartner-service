package com.autopartner.repository;

import com.autopartner.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

  User findOneByEmail(String email);

  @Query(value = "select * from users where company_id=? and active=true", nativeQuery = true)
  List<User> findAll(Long companyId);

  @Query(value = "select * from users where id=?1 and company_id=?2 and active=true", nativeQuery = true)
  Optional<User> findById(Long id, Long companyId);

  @Query(value = "select id from users where email=? and active=true", nativeQuery = true)
  Optional<Long> findIdByEmail(String email);
}
