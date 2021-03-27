package com.autopartner.repository;

import com.autopartner.domain.CarBrand;
import org.springframework.data.repository.CrudRepository;

public interface CarBrandRepository extends CrudRepository<CarBrand, Long> {

  Iterable<CarBrand> findByActiveTrue();
}
