package com.autopartner.repository;

import com.autopartner.domain.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends CrudRepository<Car, Long> {

  List<Car> findAllByActiveTrue();
  Optional<Car> findByIdAndActiveTrue(Long id);
}
