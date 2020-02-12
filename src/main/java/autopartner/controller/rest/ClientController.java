package autopartner.controller.rest;

import autopartner.domain.entity.Client;
import autopartner.service.ClientService;
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
public class ClientController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClientService clientService;

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = {"/api/client"}, method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(clientService.getByActiveTrue());
    }

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = "/api/client/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable Integer id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = "/api/client/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok(true);
    }

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @RequestMapping(value = "/api/client", method = RequestMethod.POST)
    public ResponseEntity<?> save(@Valid @RequestBody Client client) {
        return ResponseEntity.ok(clientService.saveClient(client));
    }

}