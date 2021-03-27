package com.autopartner.repository;

import com.autopartner.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {

  Iterable<Order> findByActiveTrue();
}
