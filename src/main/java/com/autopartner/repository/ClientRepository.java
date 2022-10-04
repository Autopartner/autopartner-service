package com.autopartner.repository;

import com.autopartner.domain.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {

  @Query(value = "select * from clients where company_id=? and active=true", nativeQuery = true)
  List<Client> findAll(Long companyId);

  @Query(value = "select * from clients where id=?1 and company_id=?2 and active=true", nativeQuery = true)
  Optional<Client> findById(Long id, Long companyId);

  @Query(value = "select id from clients where phone=?1 and company_id=?2 and active=true", nativeQuery = true)
  Optional<Long> findIdByPhone(String phone, Long companyId);
}
