package com.autopartner.service.impl;

import com.autopartner.domain.OrderTask;
import com.autopartner.repository.OrderTaskRepository;
import com.autopartner.service.OrderTaskService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import static lombok.AccessLevel.PRIVATE;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OrderTaskServiceImpl implements OrderTaskService {

  OrderTaskRepository orderTaskRepository;

  @Override
  public Iterable<OrderTask> getByActiveTrue() {
    return orderTaskRepository.findByActiveTrue();
  }

  @Override
  public Iterable<OrderTask> getByOrderAndActiveTrue(Long orderId) {
    return orderTaskRepository.findByOrderAndActiveTrue(orderId);
  }

  @Override
  public OrderTask getOrderTaskById(Long id) {
    return orderTaskRepository.findById(id).get();
  }

  @Override
  public OrderTask saveOrderTask(OrderTask orderTask) {
    return orderTaskRepository.save(orderTask);
  }
}
