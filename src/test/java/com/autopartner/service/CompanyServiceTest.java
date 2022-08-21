package com.autopartner.service;

import com.autopartner.api.dto.CompanyRegistrationRequest;
import com.autopartner.api.dto.CompanyRegistrationRequestFixture;
import com.autopartner.domain.Company;
import com.autopartner.domain.CompanyFixture;
import com.autopartner.domain.User;
import com.autopartner.repository.CompanyRepository;
import com.autopartner.repository.UserRepository;
import com.autopartner.service.impl.CompanyServiceImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PACKAGE)
class CompanyServiceTest {

  @Mock
  CompanyRepository companyRepository;
  @Mock
  UserRepository userRepository;
  @Mock
  PasswordEncoder passwordEncoder;
  @InjectMocks
  CompanyServiceImpl companyService;
  @Captor
  ArgumentCaptor<Company> companyArgumentCaptor;
  @Captor
  ArgumentCaptor<User> userArgumentCaptor;
  @Captor
  ArgumentCaptor<Long> longArgumentCaptor;
  CompanyRegistrationRequest companyRegistrationRequest;
  List<Company> companies;
  Company company;

  @BeforeEach
  public void init() {
    company = CompanyFixture.createCompany();
    companyRegistrationRequest = CompanyRegistrationRequestFixture.createCompanyRegistrationRequestWithoutPassword();
    companies = List.of(company, new Company());
  }

  @Test
  void create() {

    String password = "password";
    when(companyRepository.save(any())).thenReturn(company);
    when(passwordEncoder.encode(any())).thenReturn(password);

    companyService.create(companyRegistrationRequest);
    verify(companyRepository).save(companyArgumentCaptor.capture());
    Company actualCompany = companyArgumentCaptor.getValue();
    assertThat(actualCompany.getName()).isEqualTo(companyRegistrationRequest.getName());
    assertThat(actualCompany.getCountry()).isEqualTo(companyRegistrationRequest.getCountry());
    assertThat(actualCompany.getCity()).isEqualTo(companyRegistrationRequest.getCity());

    verify(userRepository).save(userArgumentCaptor.capture());
    verify(passwordEncoder).encode(companyRegistrationRequest.getPassword());
    User actualUser = userArgumentCaptor.getValue();
    assertThat(actualUser.getFirstName()).isEqualTo(companyRegistrationRequest.getFirstName());
    assertThat(actualUser.getLastName()).isEqualTo(companyRegistrationRequest.getLastName());
    assertThat(actualUser.getEmail()).isEqualTo(companyRegistrationRequest.getEmail());
    assertThat(actualUser.getPhone()).isEqualTo(companyRegistrationRequest.getPhone());
    assertThat(actualUser.getCompanyId()).isEqualTo(company.getId());
    assertThat(actualUser.getPassword()).isEqualTo(password);

  }

  @Test
  void findAll() {
    when(companyRepository.findAllByActiveTrue()).thenReturn(companies);
    List<Company> actualCompanies = companyService.findAll();
    assertThat(companies).isEqualTo(actualCompanies);
  }

  @Test
  void delete() {
    companyService.delete(company);
    verify(companyRepository).save(companyArgumentCaptor.capture());
    Company actualCompany = companyArgumentCaptor.getValue();
    assertThat(actualCompany).isEqualTo(company);
    assertThat(actualCompany.getActive()).isFalse();
  }
}