package autopartner.controller.rest;

import autopartner.domain.entity.CarBrand;
import autopartner.service.CarBrandService;
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
public class CarBrandController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CarBrandService carBrandService;

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = {"/api/car/brand"}, method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(carBrandService.getByActiveTrue());
    }

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = "/api/car/brand/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity.ok(carBrandService.getCarBrandById(id));
    }

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = "/api/car/brand", method = RequestMethod.POST)
    public ResponseEntity<?> save(@Valid @RequestBody CarBrand carBrand) {
        return ResponseEntity.ok(carBrandService.saveCarBrand(carBrand));
    }
}