package com.autopartner.api.controller;

import com.autopartner.api.dto.CompanyRegistrationRequest;
import com.autopartner.api.dto.CompanyRegistrationResponse;
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
import java.util.NoSuchElementException;

import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CompanyController {

  CompanyService companyService;
  UserService userService;

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @GetMapping()
  public Iterable<Company> getAll() {
    return companyService.listAllCompanies();
  }

  @GetMapping(value = "/{id}")
  public Company getCompany(@PathVariable Long id) {
    Company company = companyService.getCompanyById(id);
    if (company == null) {
      throw new NoSuchElementException("Company does not exist");
    }
    return company;
  }

  @PostMapping
  public CompanyRegistrationResponse create(@Valid @RequestBody CompanyRegistrationRequest request) {
    log.info("Received company registration request {}", request);
    if (nonNull(userService.getUserByEmail(request.getEmail()))) {
      log.error("User already exists with email: {}", request.getEmail());
      throw new UserAlreadyExistsException("User already exists with email: " + request.getEmail());
    }

    Company company = companyService.createCompany(request);
    log.info("Created new company {}", request.getCompanyName());
    return CompanyRegistrationResponse.createResponse(company);
  }

  @Secured("ROLE_ADMIN")
  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable Long id) {
    if (companyService.getCompanyById(id) != null) {
      companyService.deleteCompany(id);
    } else {
      throw new NoSuchElementException("Company does not exist");
    }
  }

}
