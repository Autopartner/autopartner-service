package autopartner.service;

import autopartner.domain.entity.OrderMaterial;

public interface OrderMaterialService {

    Iterable<OrderMaterial> getByActiveTrue();

    OrderMaterial getOrderMaterialById(Long id);

    OrderMaterial saveOrderMaterial(OrderMaterial orderMaterial);

}
