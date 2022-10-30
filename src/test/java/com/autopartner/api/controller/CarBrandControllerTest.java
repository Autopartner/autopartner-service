package com.autopartner.api.controller;

import com.autopartner.api.dto.request.CarBrandRequest;
import com.autopartner.api.dto.request.CarBrandRequestFixture;
import com.autopartner.api.dto.response.CarBrandResponse;
import com.autopartner.api.dto.response.ErrorResponse;
import com.autopartner.domain.CarBrand;
import com.autopartner.domain.CarBrandFixture;
import com.autopartner.service.CarBrandService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import static com.autopartner.api.configuration.WebConfiguration.UNAUTHORIZED_RESPONSE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CarBrandController.class)
@AutoConfigureMockMvc
public class CarBrandControllerTest extends AbstractControllerTest {

  public static final String URL = "/api/v1/car-brands";

  @MockBean
  CarBrandService carBrandService;

  @Test
  void getAll_NotAuthorized_ReturnsClientError() throws Exception {
    this.mockMvc.perform(get(URL))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(UNAUTHORIZED_RESPONSE)));
  }

  @Test
  void getAll_Authorized_ReturnsCaBrands() throws Exception {
    CarBrand brand = CarBrandFixture.createCarBrand();
    CarBrandResponse response = CarBrandResponse.fromEntity(brand);
    List<CarBrandResponse> responses = List.of(response);
    when(carBrandService.findAll(any())).thenReturn(List.of(brand));
    this.mockMvc.perform(auth(get(URL)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(responses)));
  }

  @Test
  void get_ValidCarBrandId_ReturnsCarBrand() throws Exception {
    CarBrand brand = CarBrandFixture.createCarBrand();
    CarBrandResponse response = CarBrandResponse.fromEntity(brand);
    long id = 1L;
    when(carBrandService.findById(eq(id), any())).thenReturn(Optional.of(brand));
    this.mockMvc.perform(auth(get(URL + "/" + id)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void get_InvalidCarBrandId_ReturnsError() throws Exception {
    long id = 1L;
    when(carBrandService.findById(eq(id), any())).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "CarBrand with id=1 is not found");
    this.mockMvc.perform(auth(get(URL + "/" + id)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void create_CarBrandAlreadyExists_ReturnsError() throws Exception {
    CarBrandRequest request = CarBrandRequestFixture.createCarBrandRequest();
    when(carBrandService.findIdByName(eq(request.getName()), any())).thenReturn(Optional.of(1L));
    ErrorResponse errorResponse = new ErrorResponse(400, 402, "CarBrand with param: Audi already exists");
    this.mockMvc.perform(auth(post(URL))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void create_ValidRequest_CreatesCarBrand() throws Exception {
    CarBrand brand = CarBrandFixture.createCarBrand();
    CarBrandRequest request = CarBrandRequestFixture.createCarBrandRequest();
    CarBrandResponse response = CarBrandResponse.fromEntity(brand);
    when(carBrandService.findIdByName(eq(request.getName()), any())).thenReturn(Optional.empty());
    when(carBrandService.create(eq(request), any())).thenReturn(brand);
    this.mockMvc.perform(auth(post(URL))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void update_InvalidCarBrandId_ReturnsError() throws Exception {
    CarBrandRequest request = CarBrandRequestFixture.createCarBrandRequest();
    long id = 1L;
    when(carBrandService.findById(eq(id), any())).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "CarBrand with id=1 is not found");
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_CarBrandAlreadyExists_ReturnsError() throws Exception {
    CarBrand brand = CarBrandFixture.createCarBrand();
    CarBrandRequest request = CarBrandRequestFixture.createCarBrandRequest();
    long id = 1L;
    when(carBrandService.findById(eq(id), any())).thenReturn(Optional.of(brand));
    when(carBrandService.findIdByName(eq(request.getName()), any())).thenReturn(Optional.of(12L));
    ErrorResponse errorResponse = new ErrorResponse(400, 402, "CarBrand with param: Audi already exists");
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_ValidRequest_UpdatesCarBrand() throws Exception {
    CarBrand brand = CarBrandFixture.createCarBrand();
    CarBrandRequest request = CarBrandRequestFixture.createCarBrandRequest();
    CarBrandResponse response = CarBrandResponse.fromEntity(brand);
    long id = 1L;
    when(carBrandService.findById(eq(id), any())).thenReturn(Optional.of(brand));
    when(carBrandService.update(brand, request)).thenReturn(brand);
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void delete_InvalidCarBrandId_ReturnsError() throws Exception {
    CarBrandRequest request = CarBrandRequestFixture.createCarBrandRequest();
    long id = 1L;
    when(carBrandService.findById(eq(id), any())).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "CarBrand with id=1 is not found");
    this.mockMvc.perform(auth(delete(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void delete_ValidRequest_DeletesCarBrand() throws Exception {
    CarBrand brand = CarBrandFixture.createCarBrand();
    long id = 1L;
    when(carBrandService.findById(eq(id), any())).thenReturn(Optional.of(brand));
    this.mockMvc.perform(auth(delete(URL + "/" + id)))
        .andExpect(status().is2xxSuccessful());
  }
}
