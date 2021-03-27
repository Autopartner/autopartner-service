package com.autopartner.service;

import com.autopartner.domain.OrderTask;

public interface OrderTaskService {

  Iterable<OrderTask> getByActiveTrue();

  Iterable<OrderTask> getByOrderAndActiveTrue(Long orderId);

  OrderTask getOrderTaskById(Long id);

  OrderTask saveOrderTask(OrderTask orderTask);

}
