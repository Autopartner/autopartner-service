package com.autopartner.service.impl;

import com.autopartner.api.dto.request.CompanyRegistrationRequest;
import com.autopartner.api.dto.request.CompanyRequest;
import com.autopartner.domain.Company;
import com.autopartner.domain.User;
import com.autopartner.repository.CompanyRepository;
import com.autopartner.repository.UserRepository;
import com.autopartner.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CompanyServiceImpl implements CompanyService {

  CompanyRepository companyRepository;
  UserRepository userRepository;
  PasswordEncoder passwordEncoder;

  @Override
  public List<Company> findAll() {
    return companyRepository.findAllByActiveTrue();
  }

  @Override
  public Optional<Company> findById(Long id) {
    return companyRepository.findByIdAndActiveTrue(id);
  }

  private Company save(Company company) {
    return companyRepository.save(company);
  }

  @Override
  public Company update(Company company, CompanyRequest companyRequest) {
    company.update(companyRequest);
    return save(company);
  }

  @Override
  public void delete(Company company) {
    company.delete();
    save(company);
  }

  @Override
  @Transactional
  public Company create(CompanyRegistrationRequest request) {
    Company company = save(Company.create(request));
    User user = User.create(request, passwordEncoder.encode(request.getPassword()), company.getId());
    userRepository.save(user);
    return company;
  }
}
