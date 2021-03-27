package com.autopartner.controller.rest;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.domain.OrderMaterial;
import com.autopartner.service.OrderMaterialService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OrderMaterialController {

  OrderMaterialService orderMaterialService;

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = {"/api/order/material"}, method = RequestMethod.GET)
  public ResponseEntity<Iterable<OrderMaterial>> getAll() {
    return ResponseEntity.ok(orderMaterialService.getByActiveTrue());
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = "/api/order/material/{id}", method = RequestMethod.GET)
  public ResponseEntity<OrderMaterial> get(@PathVariable Long id) {
    return ResponseEntity.ok(orderMaterialService.getOrderMaterialById(id));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = "/api/order/material", method = RequestMethod.POST)
  public ResponseEntity<OrderMaterial> save(@Valid @RequestBody OrderMaterial orderMaterial) {
    return ResponseEntity.ok(orderMaterialService.saveOrderMaterial(orderMaterial));
  }
}
