package com.autopartner.repository;

import com.autopartner.domain.MaterialType;
import org.springframework.data.repository.CrudRepository;

public interface MaterialTypeRepository extends CrudRepository<MaterialType, Long> {

  Iterable<MaterialType> findByActiveTrue();
}
