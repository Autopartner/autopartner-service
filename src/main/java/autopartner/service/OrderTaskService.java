package autopartner.service;

import autopartner.domain.entity.OrderTask;

public interface OrderTaskService {

    Iterable<OrderTask> getByActiveTrue();

    Iterable<OrderTask> getByOrderAndActiveTrue(Long orderId);

    OrderTask getOrderTaskById(Long id);

    OrderTask saveOrderTask(OrderTask orderTask);

}
