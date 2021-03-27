package com.autopartner.service.impl;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.domain.OrderMaterial;
import com.autopartner.repository.OrderMaterialRepository;
import com.autopartner.service.OrderMaterialService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OrderMaterialServiceImpl implements OrderMaterialService {

  OrderMaterialRepository orderMaterialRepository;

  @Override
  public Iterable<OrderMaterial> getByActiveTrue() {
    return orderMaterialRepository.findByActiveTrue();
  }

  @Override
  public OrderMaterial getOrderMaterialById(Long id) {
    return orderMaterialRepository.findById(id).get();
  }

  @Override
  public OrderMaterial saveOrderMaterial(OrderMaterial orderMaterial) {
    return orderMaterialRepository.save(orderMaterial);
  }
}
