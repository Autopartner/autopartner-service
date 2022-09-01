package com.autopartner.api.controller;

import com.autopartner.api.dto.request.CompanyRegistrationRequest;
import com.autopartner.api.dto.request.CompanyRequest;
import com.autopartner.api.dto.response.CompanyResponse;
import com.autopartner.domain.Company;
import com.autopartner.exception.AlreadyExistsException;
import com.autopartner.exception.NotFoundException;
import com.autopartner.service.CompanyService;
import com.autopartner.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
  public CompanyResponse get(@PathVariable Long id) {
    return companyService.findById(id)
        .map(CompanyResponse::fromEntity)
        .orElseThrow(() -> new NotFoundException("Company", id));
  }

  @PostMapping
  public CompanyResponse create(@Valid @RequestBody CompanyRegistrationRequest request) {
    log.info("Received company registration request {}", request);
    String email = request.getEmail();
    if (userService.existsByEmail(email)) {
      throw new AlreadyExistsException("User", email);
    }
    Company company = companyService.create(request);
    log.info("Created new company {}", request.getName());
    return CompanyResponse.fromEntity(company);
  }

  @PutMapping("/{id}")
  @Secured("ROLE_USER")
  public CompanyResponse update(@PathVariable Long id, @RequestBody @Valid CompanyRequest companyRequest) {
    Company company = companyService.findById(id)
        .orElseThrow(() -> new NotFoundException("Company", id));
    return CompanyResponse.fromEntity(companyService.update(company, companyRequest));
  }

  @Secured("ROLE_ADMIN")
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    Company company = companyService.findById(id)
        .orElseThrow(() -> new NotFoundException("Company", id));
    companyService.delete(company);
  }

}
