package autopartner.repository;

import autopartner.domain.entity.OrderTask;
import org.springframework.data.repository.CrudRepository;

public interface OrderTaskRepository extends CrudRepository<OrderTask, Integer> {
    Iterable<OrderTask> findByActiveTrue();

    Iterable<OrderTask> findByOrderAndActiveTrue(Long orderId);
}