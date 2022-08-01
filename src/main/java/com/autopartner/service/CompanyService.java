package com.autopartner.service;

import com.autopartner.controller.dto.CompanyRegistrationRequest;
import com.autopartner.domain.Company;
import com.autopartner.exception.NotActiveException;

public interface CompanyService {

    Iterable<Company> listAllCompanies();

    Iterable<Company> getByActiveTrue();

    Company getCompanyById(Long id) throws NotActiveException;

    Company saveCompany(Company company);

    void deleteCompany(Long id);

    Company createCompany(CompanyRegistrationRequest request);
}
