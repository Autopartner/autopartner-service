package com.autopartner.repository;

import com.autopartner.domain.CarModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CarModelRepository extends CrudRepository<CarModel, Long> {

  List<CarModel> findByActiveTrue();

  Optional<CarModel> findByIdAndActiveTrue(Long id);

  @Query(value = "select id from car_models where name=? and active=true", nativeQuery = true)
  Optional<Long> findIdByNameAndActiveTrue(String name);
}
