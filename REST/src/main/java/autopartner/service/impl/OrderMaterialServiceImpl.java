package autopartner.service.impl;

import autopartner.domain.entity.OrderMaterial;
import autopartner.repository.OrderMaterialRepository;
import autopartner.service.OrderMaterialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Repository
@Transactional
@Service("orderMaterialService")
public class OrderMaterialServiceImpl implements OrderMaterialService {

    @Autowired
    private OrderMaterialRepository orderMaterialRepository;

    @Override
    public Iterable<OrderMaterial> getByActiveTrue() {
        return orderMaterialRepository.findByActiveTrue();
    }

    @Override
    public OrderMaterial getOrderMaterialById(Integer id) {
        return orderMaterialRepository.findById(id).get();
    }

    @Override
    public OrderMaterial saveOrderMaterial(OrderMaterial orderMaterial) {
        return orderMaterialRepository.save(orderMaterial);
    }
}
