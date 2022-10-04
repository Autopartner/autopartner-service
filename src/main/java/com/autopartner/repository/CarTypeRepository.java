package com.autopartner.repository;

import com.autopartner.domain.CarType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CarTypeRepository extends CrudRepository<CarType, Long> {

  @Query(value = "select * from car_types where company_id=? and active=true", nativeQuery = true)
  List<CarType> findAll(Long companyId);

  @Query(value = "select * from car_types where id=?1 and company_id=?2 and active=true", nativeQuery = true)
  Optional<CarType> findById(Long id, Long companyId);

  @Query(value = "select id from car_types where name=?1 and company_id=?2 and active=true ", nativeQuery = true)
  Optional<Long> findIdByName(String name, Long companyId);
}
