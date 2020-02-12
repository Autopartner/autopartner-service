package autopartner.controller.rest;

import autopartner.domain.entity.Material;
import autopartner.service.MaterialService;
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
public class MaterialController {

    //private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MaterialService materialService;

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = {"/api/material"}, method = RequestMethod.GET)
    public ResponseEntity<Iterable<Material>> getAll() {
        return ResponseEntity.ok(materialService.getByActiveTrue());
    }

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = "/api/material/{id}", method = RequestMethod.GET)
    public ResponseEntity<Material> get(@PathVariable Integer id) {
        return ResponseEntity.ok(materialService.getMaterialById(id));
    }

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = "/api/material", method = RequestMethod.POST)
    public ResponseEntity<Material> save(@Valid @RequestBody Material material) {
        return ResponseEntity.ok(materialService.saveMaterial(material));
    }
}