package com.autopartner.repository;

import com.autopartner.domain.InputMaterial;
import org.springframework.data.repository.CrudRepository;

public interface InputMaterialRepository extends CrudRepository<InputMaterial, Long> {

  Iterable<InputMaterial> findByActiveTrue();
}
