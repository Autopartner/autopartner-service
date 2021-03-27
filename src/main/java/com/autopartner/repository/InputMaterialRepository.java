package com.autopartner.repository;

import com.autopartner.domain.InputMaterial;
import com.autopartner.domain.OrderMaterial;
import org.springframework.data.repository.CrudRepository;

public interface InputMaterialRepository extends CrudRepository<InputMaterial, Long> {

  Iterable<InputMaterial> findByActiveTrue();
}
