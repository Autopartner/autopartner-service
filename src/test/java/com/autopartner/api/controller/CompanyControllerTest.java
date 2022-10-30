package com.autopartner.api.controller;

import com.autopartner.api.dto.request.CompanyRegistrationRequest;
import com.autopartner.api.dto.request.CompanyRequest;
import com.autopartner.api.dto.response.CompanyResponse;
import com.autopartner.api.dto.response.ErrorResponse;
import com.autopartner.domain.Company;
import com.autopartner.domain.CompanyFixture;
import com.autopartner.service.CompanyService;
import com.autopartner.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util. Objects;
import java.util.Optional;

import static com.autopartner.api.dto.request.CompanyRegistrationRequestFixture.createCompanyRegistrationRequest;
import static com.autopartner.api.dto.request.CompanyRegistrationRequestFixture.createCompanyRegistrationRequestWithoutPassword;
import static com.autopartner.api.dto.request.CompanyRequestFixture.createCompanyRequest;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CompanyController.class)
@AutoConfigureMockMvc
public class CompanyControllerTest extends AbstractControllerTest {

  public static final String URL = "/api/v1/companies";

  @MockBean
  CompanyService companyService;

  @MockBean
  UserService userService;

  @Test
  void get_ValidCompanyId_ReturnsCompany() throws Exception {
    Company company = CompanyFixture.createCompany();
    long companyId = 1L;
    when(companyService.findById(companyId)).thenReturn(Optional.ofNullable(company));
    CompanyResponse companyResponse = CompanyResponse.fromEntity(Objects.requireNonNull(company));
    this.mockMvc.perform(auth(get(URL + "/" + companyId)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(companyResponse)));
  }

  @Test
  void get_InvalidCompanyId_ReturnsError() throws Exception {
    long companyId = 1L;
    when(companyService.findById(companyId)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "Company with id=1 is not found");
    this.mockMvc.perform(auth(get(URL + "/" + companyId)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void create_EmptyPassword_ReturnsError() throws Exception {
    CompanyRegistrationRequest request = createCompanyRegistrationRequestWithoutPassword();
    ErrorResponse errorResponse = new ErrorResponse(400, 402, "password=null, Password cannot be empty");
    this.mockMvc.perform(post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void create_UserAlreadyExists_ReturnsError() throws Exception {
    CompanyRegistrationRequest request = createCompanyRegistrationRequest();
    when(userService.findIdByEmail(request.getEmail())).thenReturn(Optional.of(1L));
    ErrorResponse errorResponse = new ErrorResponse(400, 402, "User with param: company@gmail.com already exists");
    this.mockMvc.perform(post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void create_ValidRequest_CreatesCompany() throws Exception {
    CompanyRegistrationRequest request = createCompanyRegistrationRequest();
    when(userService.findIdByEmail(request.getEmail())).thenReturn(Optional.empty());
    Company company = CompanyFixture.createCompany();
    CompanyResponse companyResponse = CompanyResponse.fromEntity(company);
    when(companyService.create(request)).thenReturn(company);
    this.mockMvc.perform(post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(companyResponse)));
  }

  @Test
  void update_InvalidCompanyId_ReturnsError() throws Exception {
    CompanyRequest request = createCompanyRequest();
    long companyId = 1L;
    when(companyService.findById(companyId)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "Company with id=1 is not found");
    this.mockMvc.perform(auth(put(URL + "/" + companyId))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_ValidRequest_UpdatesCompany() throws Exception {
    CompanyRequest request = createCompanyRequest();
    Company company = CompanyFixture.createCompany();
    long companyId = 1L;
    CompanyResponse companyResponse = CompanyResponse.fromEntity(company);
    when(companyService.findById(companyId)).thenReturn(Optional.of(company));
    when(companyService.update(company, request)).thenReturn(company);
    this.mockMvc.perform(auth(put(URL + "/" + companyId))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(companyResponse)));
  }

  @Test
  void delete_InvalidCompanyId_ReturnsError() throws Exception {
    CompanyRequest request = createCompanyRequest();
    long companyId = 1L;
    when(companyService.findById(companyId)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "Company with id=1 is not found");
    this.mockMvc.perform(auth(delete(URL + "/" + companyId))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void delete_ValidRequest_DeletesCompany() throws Exception {
    Company company = CompanyFixture.createCompany();
    long companyId = 1L;
    when(companyService.findById(companyId)).thenReturn(Optional.of(company));
    this.mockMvc.perform(auth(delete(URL + "/" + companyId)))
        .andExpect(status().is2xxSuccessful());
  }

}
