package com.autopartner.repository;

import com.autopartner.domain.CarType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CarTypeRepository extends CrudRepository<CarType, Long> {

  List<CarType> findByActiveTrue();

  Optional<CarType> findByIdAndActiveTrue(Long id);

  @Query(value = "select id from car_types where name=? and active=true", nativeQuery = true)
  Optional<Long> findIdByNameAndActiveTrue(String name);
}
