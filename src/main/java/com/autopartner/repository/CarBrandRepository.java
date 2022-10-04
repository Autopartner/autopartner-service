package com.autopartner.repository;

import com.autopartner.domain.CarBrand;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CarBrandRepository extends CrudRepository<CarBrand, Long> {

  @Query(value = "select * from car_brands where company_id=? and active=true", nativeQuery = true)
  List<CarBrand> findAll(Long companyId);
  @Query(value = "select * from car_brands where id=?1 and company_id=?2 and active=true", nativeQuery = true)
  Optional<CarBrand> findById(Long id, Long companyId);

  @Query(value = "select id from car_brands where name=?1 and company_id=?2 and active=true", nativeQuery = true)
  Optional<Long> findIdByNameAndActiveTrue(String name, Long companyId);
}
