package com.autopartner.controller.rest;

import com.autopartner.DTO.CompanyRegistrationRequest;
import com.autopartner.domain.Company;
import com.autopartner.domain.User;
import com.autopartner.service.impl.CompanyServiceImpl;
import com.autopartner.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static lombok.AccessLevel.PRIVATE;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CompanyController {

    CompanyServiceImpl companyService;
    UserServiceImpl userService;

    @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
    @GetMapping(value = "/api/company")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(companyService.listAllCompanies());
    }

    @PostMapping(value = "/api/company/registration")
    public ResponseEntity<?> newCompany(@Valid @RequestBody CompanyRegistrationRequest companyRegistrationRequest) {
        companyService.saveCompany(Company.createCompany(companyRegistrationRequest));
        userService.saveUser(User.createUser(companyRegistrationRequest));
        return ResponseEntity.ok(companyRegistrationRequest);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/api/company/{id}")
    public void deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
    }


}
