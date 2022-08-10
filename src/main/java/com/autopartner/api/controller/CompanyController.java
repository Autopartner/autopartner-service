package com.autopartner.api.controller;

import com.autopartner.api.dto.CompanyRegistrationRequest;
import com.autopartner.api.dto.CompanyRegistrationResponse;
import com.autopartner.api.dto.CompanyRequest;
import com.autopartner.api.dto.CompanyResponse;
import com.autopartner.domain.Company;
import com.autopartner.exception.UserAlreadyExistsException;
import com.autopartner.service.CompanyService;
import com.autopartner.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CompanyController {

  CompanyService companyService;
  UserService userService;

  @Secured("ROLE_ADMIN")
  @GetMapping()
  public List<CompanyResponse> getAll() {
    return companyService.findAll().stream()
        .map(CompanyResponse::fromEntity)
        .collect(Collectors.toList());
  }

  @Secured("ROLE_USER")
  @GetMapping(value = "/{id}")
  public CompanyResponse getCompany(@PathVariable Long id) {
    return companyService.findById(id)
        .map(CompanyResponse::fromEntity)
        .orElseThrow(() -> new NoSuchElementException("Company does not exist: " + id));
  }

  @PostMapping
  public CompanyRegistrationResponse create(@Valid @RequestBody CompanyRegistrationRequest request) {
    log.info("Received company registration request {}", request);
    String email = request.getEmail();
    if (userService.existsByEmail(email)) {
      log.error("User already exists with email: {}", email);
      throw new UserAlreadyExistsException("User already exists with email: " + email);
    }

    Company company = companyService.create(request);
    log.info("Created new company {}", request.getName());
    return CompanyRegistrationResponse.createResponse(company);
  }

  @PutMapping("/{id}")
  @Secured("ROLE_USER")
  public CompanyResponse update(@PathVariable Long id, @RequestBody @Valid CompanyRequest companyRequest) {
    Company company = companyService.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Company does not exist"));
    return CompanyResponse.fromEntity(companyService.update(company, companyRequest));
  }

  @Secured("ROLE_ADMIN")
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    Company company = companyService.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Company does not exist"));
    companyService.delete(company);
  }

}