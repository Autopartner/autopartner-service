package autopartner.controller.rest;

import autopartner.domain.entity.CarModel;
import autopartner.service.CarModelService;
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
public class CarModelController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CarModelService carModelService;

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = {"/api/car/model"}, method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(carModelService.getByActiveTrue());
    }

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = "/api/car/model/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity.ok(carModelService.getCarModelById(id));
    }

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = "/api/car/model", method = RequestMethod.POST)
    public ResponseEntity<?> save(@Valid @RequestBody CarModel carModel) {
        return ResponseEntity.ok(carModelService.saveCarModel(carModel));
    }
}