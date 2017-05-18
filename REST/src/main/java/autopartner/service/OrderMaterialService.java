package autopartner.service;

import autopartner.domain.entity.OrderMaterial;

public interface OrderMaterialService {

    Iterable<OrderMaterial> getByActiveTrue();

    OrderMaterial getOrderMaterialById(Integer id);

    OrderMaterial saveOrderMaterial(OrderMaterial orderMaterial);

}
