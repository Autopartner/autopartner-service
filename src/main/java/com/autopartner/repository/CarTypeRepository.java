package com.autopartner.repository;

import com.autopartner.domain.CarType;
import org.springframework.data.repository.CrudRepository;

public interface CarTypeRepository extends CrudRepository<CarType, Long> {

  Iterable<CarType> findByActiveTrue();
}
