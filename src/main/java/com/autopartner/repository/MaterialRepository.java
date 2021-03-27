package com.autopartner.repository;

import com.autopartner.domain.Material;
import org.springframework.data.repository.CrudRepository;

public interface MaterialRepository extends CrudRepository<Material, Long> {

  Iterable<Material> findByActiveTrue();
}
