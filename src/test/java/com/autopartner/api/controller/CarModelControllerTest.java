package com.autopartner.api.controller;

import com.autopartner.api.dto.request.CarModelRequest;
import com.autopartner.api.dto.request.CarModelRequestFixture;
import com.autopartner.api.dto.response.CarModelResponse;
import com.autopartner.api.dto.response.ErrorResponse;
import com.autopartner.domain.*;
import com.autopartner.service.CarBrandService;
import com.autopartner.service.CarModelService;
import com.autopartner.service.CarTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import static com.autopartner.api.configuration.WebConfiguration.UNAUTHORIZED_RESPONSE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CarModelController.class)
@AutoConfigureMockMvc
public class CarModelControllerTest extends AbstractControllerTest {

  public static final String URL = "/api/v1/cars/models";

  @MockBean
  CarModelService carModelService;
  @MockBean
  CarBrandService carBrandService;
  @MockBean
  CarTypeService carTypeService;
  CarModel model;
  CarType type;
  CarBrand brand;
  CarModelRequest request;
  CarModelResponse response;
  Long id;

  @BeforeEach
  public void init() {
    model = CarModelFixture.createCarModel();
    type = CarTypeFixture.createCarType();
    brand = CarBrandFixture.createCarBrand();
    request = CarModelRequestFixture.createCarModelRequest();
    response = CarModelResponse.fromEntity(model);
    id = 1L;
  }

  @Test
  void getAll_NotAuthorized_ReturnsClientError() throws Exception {
    this.mockMvc.perform(get(URL))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(UNAUTHORIZED_RESPONSE)));
  }

  @Test
  void getAll_Authorized_ReturnsCarModels() throws Exception {
    List<CarModelResponse> responses = List.of(response);
    when(carModelService.findAll()).thenReturn(List.of(model));
    this.mockMvc.perform(auth(get(URL)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(responses)));
  }

  @Test
  void get_ValidCarModelId_ReturnsCarModel() throws Exception {
    when(carModelService.findById(id)).thenReturn(Optional.ofNullable(model));
    this.mockMvc.perform(auth(get(URL + "/" + id)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void get_InvalidCarModelId_ReturnsError() throws Exception {
    when(carModelService.findById(5L)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "CarModel with id=1 is not found");
    this.mockMvc.perform(auth(get(URL + "/" + id)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void create_CarModelAlreadyExists_ReturnsError() throws Exception {
    when(carModelService.existsByName(request.getName())).thenReturn(true);
    ErrorResponse errorResponse = new ErrorResponse(400, 402, "CarModel with param: Q5 already exists");
    this.mockMvc.perform(auth(post(URL))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void create_InvalidCarTypeId_CreatesCarModel() throws Exception {
    when(carModelService.existsByName(request.getName())).thenReturn(false);
    when(carBrandService.findById(request.getCarBrandId())).thenReturn(Optional.ofNullable(brand));
    when(carTypeService.findById(request.getCarTypeId())).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "CarType with id=1 is not found");
    this.mockMvc.perform(auth(post(URL))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));

  }

  @Test
  void create_InvalidCarBrandId_CreatesCarModel() throws Exception {
    when(carModelService.existsByName(request.getName())).thenReturn(false);
    when(carBrandService.findById(request.getCarBrandId())).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "CarBrand with id=1 is not found");
    this.mockMvc.perform(auth(post(URL))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));

  }

  @Test
  void create_ValidRequest_CreatesCarModel() throws Exception {
    when(carModelService.existsByName(request.getName())).thenReturn(false);
    when(carBrandService.findById(request.getCarBrandId())).thenReturn(Optional.ofNullable(brand));
    when(carTypeService.findById(request.getCarTypeId())).thenReturn(Optional.ofNullable(type));
    when(carModelService.create(request, brand, type, model.getCompanyId())).thenReturn(model);
    this.mockMvc.perform(auth(post(URL))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void update_InvalidCarModelId_ReturnsError() throws Exception {
    when(carModelService.findById(id)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "CarModel with id=1 is not found");
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_InvalidCarBrandId_ReturnsError() throws Exception {
    when(carModelService.findById(id)).thenReturn(Optional.ofNullable(model));
    when(carBrandService.findById(id)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "CarBrand with id=1 is not found");
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_InvalidCarTypeId_ReturnsError() throws Exception {
    when(carModelService.findById(id)).thenReturn(Optional.ofNullable(model));
    when(carBrandService.findById(id)).thenReturn(Optional.ofNullable(brand));
    when(carTypeService.findById(id)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "CarType with id=1 is not found");
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_ValidRequest_UpdatesCarModel() throws Exception {
    when(carModelService.findById(id)).thenReturn(Optional.of(model));
    when(carBrandService.findById(request.getCarBrandId())).thenReturn(Optional.ofNullable(brand));
    when(carTypeService.findById(request.getCarTypeId())).thenReturn(Optional.ofNullable(type));
    when(carModelService.update(model, brand, type, request)).thenReturn(model);
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void delete_InvalidCarModelId_ReturnsError() throws Exception {
    when(carModelService.findById(id)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "CarModel with id=1 is not found");
    this.mockMvc.perform(auth(delete(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void delete_ValidRequest_DeletesCarModel() throws Exception {
    when(carModelService.findById(id)).thenReturn(Optional.of(model));
    this.mockMvc.perform(auth(delete(URL + "/" + id)))
        .andExpect(status().is2xxSuccessful());
  }
}
