package autopartner.controller.rest;

import autopartner.domain.entity.CarType;
import autopartner.service.CarTypeService;
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
public class CarTypeController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CarTypeService carTypeService;

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = {"/api/car/type"}, method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(carTypeService.getByActiveTrue());
    }

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = "/api/car/type/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity.ok(carTypeService.getCarTypeById(id));
    }

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = "/api/car/type", method = RequestMethod.POST)
    public ResponseEntity<?> save(@Valid @RequestBody CarType carType) {
        return ResponseEntity.ok(carTypeService.saveCarType(carType));
    }
}