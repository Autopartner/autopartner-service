package com.autopartner.repository;

import com.autopartner.domain.OrderMaterial;
import org.springframework.data.repository.CrudRepository;

public interface OrderMaterialRepository extends CrudRepository<OrderMaterial, Long> {

  Iterable<OrderMaterial> findByActiveTrue();
}
