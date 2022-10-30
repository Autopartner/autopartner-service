package com.autopartner.api.controller;

import com.autopartner.domain.OrderTask;
import com.autopartner.service.OrderTaskService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

import static lombok.AccessLevel.PRIVATE;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OrderTaskController {

  OrderTaskService orderTaskService;

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = {"/api/order/task"}, method = RequestMethod.GET)
  public ResponseEntity<Iterable<OrderTask>> getAll() {
    return ResponseEntity.ok(orderTaskService.getByActiveTrue());
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = {"/api/order/{id}/task"}, method = RequestMethod.GET)
  public ResponseEntity<Iterable<OrderTask>> getByOrder(@PathVariable Long id) {
    return ResponseEntity.ok(orderTaskService.getByOrderAndActiveTrue(id));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = "/api/order/task/{id}", method = RequestMethod.GET)
  public ResponseEntity<OrderTask> get(@PathVariable Long id) {
    return ResponseEntity.ok(orderTaskService.getOrderTaskById(id));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = "/api/order/task", method = RequestMethod.POST)
  public ResponseEntity<OrderTask> save(@Valid @RequestBody OrderTask orderTask) {
    return ResponseEntity.ok(orderTaskService.saveOrderTask(orderTask));
  }
}
