package autopartner.controller.rest;

import autopartner.domain.entity.MaterialType;
import autopartner.service.MaterialTypeService;
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
public class MaterialTypeController {

    //private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MaterialTypeService materialTypeService;

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