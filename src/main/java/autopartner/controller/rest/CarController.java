package autopartner.controller.rest;

import autopartner.domain.entity.Car;
import autopartner.service.CarService;
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
public class CarController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CarService carService;

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = {"/api/car"}, method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(carService.getByActiveTrue());
    }

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = "/api/car/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = "/api/car", method = RequestMethod.POST)
    public ResponseEntity<?> save(@Valid @RequestBody Car car) {
        return ResponseEntity.ok(carService.saveCar(car));
    }
}