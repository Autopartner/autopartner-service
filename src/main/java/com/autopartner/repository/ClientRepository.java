package com.autopartner.repository;

import com.autopartner.domain.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {

  List<Client> findByActiveTrue();

  Optional<Client> findByIdAndActiveTrue(Long id);

  @Query(value = "select id from clients where phone=? and active=true", nativeQuery = true)
  Optional<Long> findIdByPhoneAndActiveTrue(String phone);
}
