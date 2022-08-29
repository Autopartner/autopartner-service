package com.autopartner.repository;

import com.autopartner.domain.CarBrand;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CarBrandRepository extends CrudRepository<CarBrand, Long> {

  List<CarBrand> findByActiveTrue();

  Optional<CarBrand> findByIdAndActiveTrue(Long id);

  boolean existsByNameAndActiveTrue(String name);
}
