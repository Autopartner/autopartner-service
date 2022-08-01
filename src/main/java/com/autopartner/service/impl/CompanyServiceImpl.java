package com.autopartner.service.impl;

import com.autopartner.configuration.WebSecurityConfiguration;
import com.autopartner.controller.dto.CompanyRegistrationRequest;
import com.autopartner.domain.Company;
import com.autopartner.domain.User;
import com.autopartner.exception.NotActiveException;
import com.autopartner.service.UserService;
import com.autopartner.repository.CompanyRepository;
import com.autopartner.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CompanyServiceImpl implements CompanyService {

  CompanyRepository companyRepository;
  UserService userService;
  @Autowired
  PasswordEncoder encoder = WebSecurityConfiguration.passwordEncoder();

  @Override
  public Iterable<Company> listAllCompanies() {
    return companyRepository.findByActiveTrue();
  }

  @Override
  public Company getCompanyById(Long id) {
    if (companyRepository.findCompanyByIdAndActiveTrue(id) == null) {
      throw new NotActiveException("Company does not active");
    }
    return companyRepository.findCompanyByIdAndActiveTrue(id);
  }

  @Override
  public Company saveCompany(Company company) {
    return companyRepository.save(company);
  }

  @Override
  public void deleteCompany(Long id) {
    Company company = companyRepository.findCompanyByIdAndActiveTrue(id);
    if (company != null) {
      company.setActive(false);
      saveCompany(company);
    }
  }

  @Override
  @Transactional
  public Company createCompany(CompanyRegistrationRequest request) {
    Company company = Company.builder()
            .companyName(request.getCompanyName())
            .country(request.getCountry())
            .city(request.getCity())
            .build();
    saveCompany(company);
    User user = User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .username(request.getEmail())
            .password(encoder.encode(request.getPassword()))
            .email(request.getEmail())
            .companyId(company.getId())
            .build();
    userService.saveUser(user);
    return company;
  }
}
