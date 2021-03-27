package com.autopartner.controller.rest;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.domain.InputMaterial;
import com.autopartner.service.InputMaterialService;
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
public class InputMaterialController {

  InputMaterialService inputMaterialService;

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = {"/api/input/material"}, method = RequestMethod.GET)
  public ResponseEntity<Iterable<InputMaterial>> getAll() {
    return ResponseEntity.ok(inputMaterialService.getByActiveTrue());
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = "/api/input/material/{id}", method = RequestMethod.GET)
  public ResponseEntity<InputMaterial> get(@PathVariable Long id) {
    return ResponseEntity.ok(inputMaterialService.getById(id));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = "/api/input/material", method = RequestMethod.POST)
  public ResponseEntity<InputMaterial> save(@Valid @RequestBody InputMaterial inputMaterial) {
    return ResponseEntity.ok(inputMaterialService.saveToWarehouse(inputMaterial));
  }
}
