package com.autopartner.controller.rest;

import com.autopartner.controller.DTO.CompanyRegistrationRequest;
import com.autopartner.domain.Company;
import com.autopartner.service.CompanyService;
import com.autopartner.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.NoSuchElementException;

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

  @PostMapping(value = "/registration")
  public CompanyRegistrationRequest newCompany(@Valid @RequestBody CompanyRegistrationRequest companyRegistrationRequest, BindingResult result) {
    companyService.createCompany(companyRegistrationRequest);
      return companyRegistrationRequest;
  }

  @Secured("ROLE_ADMIN")
  @DeleteMapping(value = "/{id}")
  public void deleteCompany(@PathVariable Long id) {
    if (companyService.getCompanyById(id) != null) {
      companyService.deleteCompany(id);
    } else {
      throw new NoSuchElementException("Company does not exist");
    }
  }

}
