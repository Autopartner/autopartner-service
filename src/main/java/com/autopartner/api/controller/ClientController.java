package com.autopartner.api.controller;

import com.autopartner.api.dto.request.ClientRequest;
import com.autopartner.api.dto.response.ClientResponse;
import com.autopartner.domain.Client;
import com.autopartner.domain.User;
import com.autopartner.exception.AlreadyExistsException;
import com.autopartner.exception.NotFoundException;
import com.autopartner.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ClientController {

  ClientService clientService;

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @GetMapping
  public List<ClientResponse> getAll(@AuthenticationPrincipal User user) {
    return clientService.findAll(user.getCompanyId()).stream()
        .map(ClientResponse::fromEntity)
        .collect(Collectors.toList());
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @GetMapping(value = "/{id}")
  public ClientResponse get(@PathVariable Long id, @AuthenticationPrincipal User user) {
    return clientService.findById(id, user.getCompanyId())
        .map(ClientResponse::fromEntity)
        .orElseThrow(() -> new NotFoundException("Client", id));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @PostMapping
  public ClientResponse create(@Valid @RequestBody ClientRequest request,
                               @AuthenticationPrincipal User user) {
    log.error("Received client registration request {}", request);
    String phone = request.getPhone();
    Long companyId = user.getCompanyId();
    if (clientService.findIdByPhone(phone, companyId).isPresent()) {
      throw new AlreadyExistsException("Client", phone);
    }
    Client client = clientService.create(request, companyId);
    log.info("Created new client {}", request.getFirstName());
    return ClientResponse.fromEntity(client);
  }

  @PutMapping("/{id}")
  @Secured("ROLE_USER")
  public ClientResponse update(@PathVariable Long id, @RequestBody @Valid ClientRequest request,
                               @AuthenticationPrincipal User user) {
    Long companyId = user.getCompanyId();
    Client client = clientService.findById(id, companyId)
        .orElseThrow(() -> new NotFoundException("Client", id));
    Optional<Long> foundId = clientService.findIdByPhone(request.getPhone(), companyId);
    if (foundId.isPresent() && !foundId.get().equals(id)) {
      throw new AlreadyExistsException("Client", request.getPhone());
    }
    return ClientResponse.fromEntity(clientService.update(client, request));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
    Client client = clientService.findById(id, user.getCompanyId())
        .orElseThrow(() -> new NotFoundException("Client", id));
    clientService.delete(client);
  }
}
