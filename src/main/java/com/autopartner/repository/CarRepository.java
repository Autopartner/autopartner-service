package com.autopartner.repository;

import com.autopartner.domain.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends CrudRepository<Car, Long> {

  @Query(value = "select * from cars where company_id= :companyId and active=true", nativeQuery = true)
  List<Car> findAll(@Param("companyId") Long companyId);
  @Query(value = "select * from cars where id= :id and company_id= :companyId and active=true", nativeQuery = true)
  Optional<Car> findById(@Param("id") Long id, @Param("companyId") Long companyId);

  @Query(value = "select id from cars where vin_code= :vinCode and company_id= :companyId and active=true", nativeQuery = true)
  Optional<Long> findIdByVinCode(@Param("vinCode")String vinCode, @Param("companyId") Long companyId);
}
