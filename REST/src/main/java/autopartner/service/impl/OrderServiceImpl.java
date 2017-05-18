package autopartner.service.impl;

import autopartner.domain.entity.Order;
import autopartner.repository.OrderRepository;
import autopartner.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Repository
@Transactional
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Iterable<Order> getByActiveTrue() {
        return orderRepository.findByActiveTrue();
    }

    @Override
    public Order getOrderById(Integer id) {
        return orderRepository.findOne(id);
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
}
