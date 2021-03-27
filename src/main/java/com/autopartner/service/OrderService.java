package com.autopartner.service;

import com.autopartner.domain.Order;

public interface OrderService {

  Iterable<Order> getByActiveTrue();

  Order getOrderById(Long id);

  Order saveOrder(Order order);

}
