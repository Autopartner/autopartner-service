package com.autopartner.repository;

import com.autopartner.domain.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends CrudRepository<Car, Long> {

  @Query(value = "select * from cars where company_id=? and active=true", nativeQuery = true)
  List<Car> findAll(Long companyId);
  @Query(value = "select * from cars where id=?1 and company_id=?2 and active=true", nativeQuery = true)
  Optional<Car> findById(Long id, Long companyId);

  @Query(value = "select id from cars where vin_code=?1 and company_id=?2 and active=true", nativeQuery = true)
  Optional<Long> findIdByVinCode(String vinCode, Long companyId);
}
