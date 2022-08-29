package com.autopartner.api.controller;

import com.autopartner.api.dto.request.ClientRequest;
import com.autopartner.api.dto.response.ClientResponse;
import com.autopartner.domain.Client;
import com.autopartner.domain.User;
import com.autopartner.exception.ClientAlreadyExistsException;
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
    public List<ClientResponse> getAll() {
        return clientService.findAll().stream()
                .map(ClientResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @GetMapping(value = "/{id}")
    public ClientResponse get(@PathVariable Long id) {
        return clientService.findById(id)
                .map(ClientResponse::fromEntity)
                .orElseThrow(() -> new NotFoundException("Client", id));
    }

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @PostMapping
    public ClientResponse create(@Valid @RequestBody ClientRequest request,
                                 @AuthenticationPrincipal User user) {
        log.error("Received client registration request {}", request);
        String phone = request.getPhone();
        if(clientService.existsByPhone(phone)){
            log.info("Client already exist with phone: {}", phone);
            throw new ClientAlreadyExistsException("Client already exist with phone: " + phone);
        }
        Client client = clientService.create(request, user.getCompanyId());
        log.info("Created new client {}", request.getFirstName());
        return ClientResponse.fromEntity(client);
    }

    @PutMapping("/{id}")
    @Secured("ROLE_USER")
    public ClientResponse update(@PathVariable Long id, @RequestBody @Valid ClientRequest request) {
        Client client = clientService.findById(id)
                .orElseThrow(() -> new NotFoundException("Client", id));
        return ClientResponse.fromEntity(clientService.update(client, request));
    }

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
     Client client = clientService.findById(id)
             .orElseThrow(() -> new NotFoundException("Client", id));
     clientService.delete(client);
    }

}
