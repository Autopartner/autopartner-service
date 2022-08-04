package com.autopartner.controller.rest;

import com.autopartner.controller.dto.CompanyRegistrationRequest;
import com.autopartner.controller.dto.CompanyRegistrationResponse;
import com.autopartner.domain.Company;
import com.autopartner.exception.UserAlreadyExistsException;
import com.autopartner.service.CompanyService;
import com.autopartner.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/api/company")
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

  @PostMapping
  public CompanyRegistrationResponse create(@Valid @RequestBody CompanyRegistrationRequest request) {

    if (nonNull(userService.getUserByEmail(request.getEmail()))) {
      throw new UserAlreadyExistsException("User already exists with email: " + request.getEmail());
    }

    Company company = companyService.createCompany(request);
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
