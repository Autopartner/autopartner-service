package com.autopartner.controller.rest;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.domain.CarBrand;
import com.autopartner.service.CarBrandService;
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
public class CarBrandController {

  CarBrandService carBrandService;

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
