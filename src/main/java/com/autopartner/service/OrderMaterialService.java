package com.autopartner.service;

import com.autopartner.domain.OrderMaterial;

public interface OrderMaterialService {

  Iterable<OrderMaterial> getByActiveTrue();

  OrderMaterial getOrderMaterialById(Long id);

  OrderMaterial saveOrderMaterial(OrderMaterial orderMaterial);

}
