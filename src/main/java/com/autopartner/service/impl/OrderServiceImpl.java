package com.autopartner.service.impl;

import com.autopartner.domain.Order;
import com.autopartner.repository.OrderRepository;
import com.autopartner.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import static lombok.AccessLevel.PRIVATE;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {

  OrderRepository orderRepository;

  @Override
  public Iterable<Order> getByActiveTrue() {
    return orderRepository.findByActiveTrue();
  }

  @Override
  public Order getOrderById(Long id) {
    return orderRepository.findById(id).get();
  }

  @Override
  public Order saveOrder(Order order) {
    return orderRepository.save(order);
  }
}
