package com.autopartner.api.controller;

import com.autopartner.domain.Material;
import com.autopartner.service.MaterialService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

import static lombok.AccessLevel.PRIVATE;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class MaterialController {

  MaterialService materialService;

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = {"/api/material"}, method = RequestMethod.GET)
  public ResponseEntity<Iterable<Material>> getAll() {
    return ResponseEntity.ok(materialService.getByActiveTrue());
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = "/api/material/{id}", method = RequestMethod.GET)
  public ResponseEntity<Material> get(@PathVariable Long id) {
    return ResponseEntity.ok(materialService.getMaterialById(id));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = "/api/material", method = RequestMethod.POST)
  public ResponseEntity<Material> save(@Valid @RequestBody Material material) {
    return ResponseEntity.ok(materialService.saveMaterial(material));
  }
}
