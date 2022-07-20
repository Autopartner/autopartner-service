package com.autopartner.service.impl;

import com.autopartner.domain.Company;
import com.autopartner.repository.CompanyRepository;
import com.autopartner.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CompanyServiceImpl implements CompanyService {

    CompanyRepository companyRepository;

    @Override
    public Iterable<Company> listAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Iterable<Company> getByActiveTrue() {
        return companyRepository.findByActiveTrue();
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).get();
    }

    @Override
    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}
