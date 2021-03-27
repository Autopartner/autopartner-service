package com.autopartner.repository;

import com.autopartner.domain.OrderTask;
import org.springframework.data.repository.CrudRepository;

public interface OrderTaskRepository extends CrudRepository<OrderTask, Long> {

  Iterable<OrderTask> findByActiveTrue();

  Iterable<OrderTask> findByOrderAndActiveTrue(Long orderId);
}
