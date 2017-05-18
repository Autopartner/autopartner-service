package autopartner.service;

import autopartner.domain.entity.OrderTask;

public interface OrderTaskService {

    Iterable<OrderTask> getByActiveTrue();

    OrderTask getOrderTaskById(Integer id);

    OrderTask saveOrderTask(OrderTask orderTask);

}
