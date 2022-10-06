package com.autopartner.repository;

import com.autopartner.domain.CarBrand;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarBrandRepository extends CrudRepository<CarBrand, Long> {

  @Query(value = "select * from car_brands where company_id= :companyId and active=true", nativeQuery = true)
  List<CarBrand> findAll(@Param("companyId") Long companyId);

  @Query(value = "select * from car_brands where id= :id and company_id= :companyId and active=true", nativeQuery = true)
  Optional<CarBrand> findById(@Param("id") Long id, @Param("companyId") Long companyId);

  @Query(value = "select id from car_brands where name= :name and company_id= :companyId and active=true", nativeQuery = true)
  Optional<Long> findIdByName(@Param("name") String name, @Param("companyId") Long companyId);
}
