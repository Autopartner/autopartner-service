package autopartner.controller.rest;

import autopartner.domain.entity.OrderTask;
import autopartner.service.OrderTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class OrderTaskController {

    @Autowired
    private OrderTaskService orderTaskService;

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = {"/api/order/task"}, method = RequestMethod.GET)
    public ResponseEntity<Iterable<OrderTask>> getAll() {
        return ResponseEntity.ok(orderTaskService.getByActiveTrue());
    }

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = "/api/order/task/{id}", method = RequestMethod.GET)
    public ResponseEntity<OrderTask> get(@PathVariable Integer id) {
        return ResponseEntity.ok(orderTaskService.getOrderTaskById(id));
    }

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = "/api/order/task", method = RequestMethod.POST)
    public ResponseEntity<OrderTask> save(@Valid @RequestBody OrderTask orderTask) {
        return ResponseEntity.ok(orderTaskService.saveOrderTask(orderTask));
    }
}