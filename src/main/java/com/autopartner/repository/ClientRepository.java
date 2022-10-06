package com.autopartner.repository;

import com.autopartner.domain.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {

  @Query(value = "select * from clients where company_id= :companyId and active=true", nativeQuery = true)
  List<Client> findAll(@Param("companyId") Long companyId);

  @Query(value = "select * from clients where id= :id and company_id= :companyId and active=true", nativeQuery = true)
  Optional<Client> findById(@Param("id") Long id, @Param("companyId") Long companyId);

  @Query(value = "select id from clients where phone= :phone and company_id= :companyId and active=true", nativeQuery = true)
  Optional<Long> findIdByPhone(@Param("phone") String phone, @Param("companyId") Long companyId);
}
