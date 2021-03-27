package com.autopartner.repository;

import com.autopartner.domain.CarModel;
import org.springframework.data.repository.CrudRepository;

public interface CarModelRepository extends CrudRepository<CarModel, Long> {

  Iterable<CarModel> findByActiveTrue();
}
