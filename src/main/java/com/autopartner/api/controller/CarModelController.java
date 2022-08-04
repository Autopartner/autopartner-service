package com.autopartner.api.controller;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.domain.CarModel;
import com.autopartner.service.CarModelService;
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
public class CarModelController {

  CarModelService carModelService;

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
