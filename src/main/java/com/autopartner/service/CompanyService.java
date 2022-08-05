package com.autopartner.service;

import com.autopartner.api.dto.CompanyRegistrationRequest;
import com.autopartner.api.dto.CompanyRequest;
import com.autopartner.domain.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    List<Company> findAll();

    Optional<Company> findById(Long id);

    Company update(Company company, CompanyRequest request);

    void delete(Company company);

    Company create(CompanyRegistrationRequest request);
}
