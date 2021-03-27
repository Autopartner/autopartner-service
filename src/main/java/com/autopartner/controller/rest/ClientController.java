package com.autopartner.controller.rest;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.domain.Client;
import com.autopartner.service.ClientService;
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
public class ClientController {

  ClientService clientService;

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = {"/api/client"}, method = RequestMethod.GET)
  public ResponseEntity<?> getAll() {
    return ResponseEntity.ok(clientService.getByActiveTrue());
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = "/api/client/{id}", method = RequestMethod.GET)
  public ResponseEntity<?> get(@PathVariable Long id) {
    return ResponseEntity.ok(clientService.getClientById(id));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = "/api/client/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> delete(@PathVariable Long id) {
    clientService.deleteClient(id);
    return ResponseEntity.ok(true);
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = "/api/client", method = RequestMethod.POST)
  public ResponseEntity<?> save(@Valid @RequestBody Client client) {
    return ResponseEntity.ok(clientService.saveClient(client));
  }

}
