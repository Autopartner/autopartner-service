package com.autopartner.repository;

import com.autopartner.domain.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long> {

  Iterable<Car> findByActiveTrue();
}
