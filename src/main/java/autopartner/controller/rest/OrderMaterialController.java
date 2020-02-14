package autopartner.controller.rest;

import autopartner.domain.entity.OrderMaterial;
import autopartner.service.OrderMaterialService;
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
public class OrderMaterialController {

    @Autowired
    private OrderMaterialService orderMaterialService;

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