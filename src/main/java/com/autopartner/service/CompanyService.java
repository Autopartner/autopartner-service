package com.autopartner.service;

import com.autopartner.controller.dto.CompanyRegistrationRequest;
import com.autopartner.domain.Company;

public interface CompanyService {

    Iterable<Company> listAllCompanies();

    Iterable<Company> getByActiveTrue();

    Company getCompanyById(Long id);

    Company saveCompany(Company company);

    void deleteCompany(Long id);

    void createCompany(CompanyRegistrationRequest request);
}
