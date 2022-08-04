package com.autopartner.api.controller;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.domain.MaterialType;
import com.autopartner.service.MaterialTypeService;
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
public class MaterialTypeController {

  MaterialTypeService materialTypeService;

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = {"/api/material/type"}, method = RequestMethod.GET)
  public ResponseEntity<Iterable<MaterialType>> getAll() {
    return ResponseEntity.ok(materialTypeService.getByActiveTrue());
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = "/api/material/type/{id}", method = RequestMethod.GET)
  public ResponseEntity<MaterialType> get(@PathVariable Long id) {
    return ResponseEntity.ok(materialTypeService.getMaterialTypeById(id));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = "/api/material/type", method = RequestMethod.POST)
  public ResponseEntity<MaterialType> save(@Valid @RequestBody MaterialType materialType) {
    return ResponseEntity.ok(materialTypeService.saveMaterialType(materialType));
  }
}
