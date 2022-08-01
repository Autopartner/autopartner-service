package com.autopartner.service;

import com.autopartner.controller.dto.CompanyRegistrationRequest;
import com.autopartner.domain.Company;
import com.autopartner.domain.CompanyFixture;
import com.autopartner.controller.dto.CompanyRegistrationRequestFixture;
import com.autopartner.repository.CompanyRepository;
import com.autopartner.service.impl.CompanyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

  @Mock
  private CompanyRepository companyRepository;
  @InjectMocks
  CompanyServiceImpl companyService;
  @Captor
  ArgumentCaptor<Company> companyArgumentCaptor;
  @Captor
  ArgumentCaptor<Long> longArgumentCaptor;
  CompanyRegistrationRequest companyRegistrationRequest;
  List<Company> companies;
  Company company;

  @BeforeEach
  public void init() {
    company = CompanyFixture.createCompany();
    companyRegistrationRequest = CompanyRegistrationRequestFixture.createCompanyRegistrationRequest();
    companies = List.of(company, new Company());
  }

  @Test
  void saveCompany() {
    companyService.saveCompany(company);
    verify(companyRepository).save(companyArgumentCaptor.capture());
    assertThat(company.getCompanyName()).isEqualTo(companyRegistrationRequest.getCompanyName());
    assertThat(company.getCountry()).isEqualTo(companyRegistrationRequest.getCountry());
    assertThat(company.getCity()).isEqualTo(companyRegistrationRequest.getCity());
  }

  @Test
  void findAllCompanies() {
    when(companyRepository.findAll()).thenReturn(companies);
    Iterable<Company> companyIterable = companyService.listAllCompanies();
    List<Company> companyList = StreamSupport.stream(companyIterable.spliterator(), false)
            .toList();
    assertThat(companies).isEqualTo(companyList);
    assertThat(companyList.stream().findFirst().get()).isEqualTo(company);
  }

  @Test
  void delete() {
    when(companyRepository.findById(anyLong())).thenReturn(Optional.ofNullable(company));
    companyService.deleteCompany(company.getId());
    verify(companyRepository).findById(longArgumentCaptor.capture());
    Long companyId = longArgumentCaptor.getValue();
    assertThat(company.getId()).isEqualTo(companyId);
  }

}