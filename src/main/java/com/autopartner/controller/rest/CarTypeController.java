package com.autopartner.controller.rest;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.domain.CarType;
import com.autopartner.service.CarTypeService;
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
public class CarTypeController {

  CarTypeService carTypeService;

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
