package autopartner.service;

import autopartner.domain.entity.Order;

public interface OrderService {

    Iterable<Order> getByActiveTrue();

    Order getOrderById(Integer id);

    Order saveOrder(Order order);

}
