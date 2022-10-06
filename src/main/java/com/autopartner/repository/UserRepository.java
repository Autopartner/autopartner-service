package com.autopartner.repository;

import com.autopartner.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

  User findOneByEmail(String email);

  @Query(value = "select * from users where company_id= :companyId and active=true", nativeQuery = true)
  List<User> findAll(@Param("companyId") Long companyId);

  @Query(value = "select * from users where id= :id and company_id= :companyId and active=true", nativeQuery = true)
  Optional<User> findById(@Param("id") Long id, @Param("companyId") Long companyId);

  @Query(value = "select id from users where email= :email and active=true", nativeQuery = true)
  Optional<Long> findIdByEmail(@Param("email") String email);
}
