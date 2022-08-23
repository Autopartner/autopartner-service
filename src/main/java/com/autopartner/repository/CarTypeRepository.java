package com.autopartner.repository;

import com.autopartner.domain.CarType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CarTypeRepository extends CrudRepository<CarType, Long> {

    List<CarType> findByActiveTrue();

    Optional<CarType> findByIdAndActiveTrue(Long id);

    boolean existsByNameAndActiveTrue(String name);
}
