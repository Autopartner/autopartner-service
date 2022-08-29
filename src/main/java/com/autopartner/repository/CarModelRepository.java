package com.autopartner.repository;

import com.autopartner.domain.CarModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CarModelRepository extends CrudRepository<CarModel, Long> {

    List<CarModel> findByActiveTrue();

    Optional<CarModel> findByIdAndActiveTrue(Long id);

    boolean existsByNameAndActiveTrue(String name);
}
