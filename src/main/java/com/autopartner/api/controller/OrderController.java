package com.autopartner.api.controller;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.domain.Order;
import com.autopartner.service.OrderService;
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
public class OrderController {

  OrderService orderService;

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = {"/api/order"}, method = RequestMethod.GET)
  public ResponseEntity<Iterable<Order>> getAll() {
    return ResponseEntity.ok(orderService.getByActiveTrue());
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = "/api/order/{id}", method = RequestMethod.GET)
  public ResponseEntity<Order> get(@PathVariable Long id) {
    return ResponseEntity.ok(orderService.getOrderById(id));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = "/api/order", method = RequestMethod.POST)
  public ResponseEntity<Order> save(@Valid @RequestBody Order order) {
    return ResponseEntity.ok(orderService.saveOrder(order));
  }
}
