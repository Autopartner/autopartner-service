package com.autopartner.service.impl;

import com.autopartner.configuration.WebSecurityConfiguration;
import com.autopartner.controller.DTO.CompanyRegistrationRequest;
import com.autopartner.domain.Company;
import com.autopartner.domain.User;
import com.autopartner.repository.CompanyRepository;
import com.autopartner.service.CompanyService;
import com.autopartner.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

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
    return companyRepository.findAll();
  }

  @Override
  public Iterable<Company> getByActiveTrue() {
    return companyRepository.findByActiveTrue();
  }

  @Override
  public Company getCompanyById(Long id) {
    return companyRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Company does not exist"));
  }

  @Override
  public Company saveCompany(Company company) {
    return companyRepository.save(company);
  }

  @Override
  public void deleteCompany(Long id) {
    companyRepository.deleteById(id);
  }

  @Override
  @Transactional
  public void createCompany(CompanyRegistrationRequest request) {
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
  }
}
