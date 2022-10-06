package com.autopartner.repository;

import com.autopartner.domain.CarType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarTypeRepository extends CrudRepository<CarType, Long> {

  @Query(value = "select * from car_types where company_id= :companyId and active=true", nativeQuery = true)
  List<CarType> findAll(@Param("companyId") Long companyId);

  @Query(value = "select * from car_types where id= :id and company_id= :companyId and active=true", nativeQuery = true)
  Optional<CarType> findById(@Param("id") Long id, @Param("companyId") Long companyId);

  @Query(value = "select id from car_types where name= :name and company_id= :companyId and active=true ", nativeQuery = true)
  Optional<Long> findIdByName(@Param("name") String name, @Param("companyId") Long companyId);
}
