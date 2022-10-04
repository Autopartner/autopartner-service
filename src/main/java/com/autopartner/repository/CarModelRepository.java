package com.autopartner.repository;

import com.autopartner.domain.CarModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CarModelRepository extends CrudRepository<CarModel, Long> {

  @Query(value = "select * from car_models where company_id=? and active=true", nativeQuery = true)
  List<CarModel> findAll(Long companyId);

  @Query(value = "select * from car_models where id=?1 and active=true and company_id=?2", nativeQuery = true)
  Optional<CarModel> findById(Long id, Long companyId);

  @Query(value = "select id from car_models where name=?1 and active=true and company_id=?2", nativeQuery = true)
  Optional<Long> findIdByName(String name, Long companyId);
}
