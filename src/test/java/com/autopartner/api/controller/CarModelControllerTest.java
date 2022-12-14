package com.autopartner.api.controller;

import com.autopartner.api.dto.request.CarModelRequest;
import com.autopartner.api.dto.request.CarModelRequestFixture;
import com.autopartner.api.dto.response.CarModelResponse;
import com.autopartner.api.dto.response.ErrorResponse;
import com.autopartner.domain.*;
import com.autopartner.service.CarBrandService;
import com.autopartner.service.CarModelService;
import com.autopartner.service.CarTypeService;
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

@WebMvcTest(controllers = CarModelController.class)
@AutoConfigureMockMvc
public class CarModelControllerTest extends AbstractControllerTest {

  public static final String URL = "/api/v1/car-models";

  @MockBean
  CarModelService carModelService;
  @MockBean
  CarBrandService carBrandService;
  @MockBean
  CarTypeService carTypeService;

  @Test
  void getAll_NotAuthorized_ReturnsClientError() throws Exception {
    this.mockMvc.perform(get(URL))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(UNAUTHORIZED_RESPONSE)));
  }

  @Test
  void getAll_Authorized_ReturnsCarModels() throws Exception {
    CarModel model = CarModelFixture.createCarModel();
    CarModelResponse response = CarModelResponse.fromEntity(model);
    List<CarModelResponse> responses = List.of(response);
    when(carModelService.findAll(any())).thenReturn(List.of(model));
    this.mockMvc.perform(auth(get(URL)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(responses)));
  }

  @Test
  void get_ValidCarModelId_ReturnsCarModel() throws Exception {
    CarModel model = CarModelFixture.createCarModel();
    CarModelResponse response = CarModelResponse.fromEntity(model);
    long id = 1L;
    when(carModelService.findById(eq(id), any())).thenReturn(Optional.of(model));
    this.mockMvc.perform(auth(get(URL + "/" + id)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void get_InvalidCarModelId_ReturnsError() throws Exception {
    long id = 1L;
    when(carModelService.findById(eq(id), any())).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "CarModel with id=1 is not found");
    this.mockMvc.perform(auth(get(URL + "/" + id)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void create_CarModelAlreadyExists_ReturnsError() throws Exception {
    CarModelRequest request = CarModelRequestFixture.createCarModelRequest();
    when(carModelService.findIdByName(eq(request.getName()), any())).thenReturn(Optional.of(1L));
    ErrorResponse errorResponse = new ErrorResponse(400, 402, "CarModel with param: Q5 already exists");
    this.mockMvc.perform(auth(post(URL))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void create_InvalidCarTypeId_CreatesCarModel() throws Exception {
    CarBrand brand = CarBrandFixture.createCarBrand();
    CarModelRequest request = CarModelRequestFixture.createCarModelRequest();
    when(carModelService.findIdByName(eq(request.getName()), any())).thenReturn(Optional.empty());
    when(carBrandService.findById(eq(request.getCarBrandId()), any())).thenReturn(Optional.ofNullable(brand));
    when(carTypeService.findById(eq(request.getCarTypeId()), any())).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "CarType with id=1 is not found");
    this.mockMvc.perform(auth(post(URL))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void create_InvalidCarBrandId_CreatesCarModel() throws Exception {
    CarModelRequest request = CarModelRequestFixture.createCarModelRequest();
    when(carModelService.findIdByName(eq(request.getName()), any())).thenReturn(Optional.empty());
    when(carBrandService.findById(eq(request.getCarBrandId()), any())).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "CarBrand with id=1 is not found");
    this.mockMvc.perform(auth(post(URL))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));

  }

  @Test
  void create_ValidRequest_CreatesCarModel() throws Exception {
    CarModel model = CarModelFixture.createCarModel();
    CarType type = CarTypeFixture.createCarType();
    CarBrand brand = CarBrandFixture.createCarBrand();
    CarModelRequest request = CarModelRequestFixture.createCarModelRequest();
    CarModelResponse response = CarModelResponse.fromEntity(model);
    when(carModelService.findIdByName(eq(request.getName()), any())).thenReturn(Optional.empty());
    when(carBrandService.findById(eq(request.getCarBrandId()), any())).thenReturn(Optional.ofNullable(brand));
    when(carTypeService.findById(eq(request.getCarTypeId()), any())).thenReturn(Optional.ofNullable(type));
    when(carModelService.create(eq(request), eq(brand), eq(type), any())).thenReturn(model);
    this.mockMvc.perform(auth(post(URL))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void update_InvalidCarModelId_ReturnsError() throws Exception {
    CarModelRequest request = CarModelRequestFixture.createCarModelRequest();
    long id = 1L;
    when(carModelService.findById(eq(id), any())).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "CarModel with id=1 is not found");
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_InvalidCarBrandId_ReturnsError() throws Exception {
    CarModel model = CarModelFixture.createCarModel();
    CarModelRequest request = CarModelRequestFixture.createCarModelRequest();
    long id = 1L;
    when(carModelService.findById(eq(id), any())).thenReturn(Optional.of(model));
    when(carBrandService.findById(eq(id), any())).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "CarBrand with id=1 is not found");
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_InvalidCarTypeId_ReturnsError() throws Exception {
    CarModel model = CarModelFixture.createCarModel();
    CarBrand brand = CarBrandFixture.createCarBrand();
    CarModelRequest request = CarModelRequestFixture.createCarModelRequest();
    long id = 1L;
    when(carModelService.findById(eq(id), any())).thenReturn(Optional.of(model));
    when(carBrandService.findById(eq(id), any())).thenReturn(Optional.of(brand));
    when(carTypeService.findById(eq(id), any())).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "CarType with id=1 is not found");
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_CarModelAlreadyExists_ReturnsError() throws Exception {
    CarModel model = CarModelFixture.createCarModel();
    CarBrand brand = CarBrandFixture.createCarBrand();
    CarType type = CarTypeFixture.createCarType();
    long id = 1L;
    CarModelRequest request = CarModelRequestFixture.createCarModelRequest();
    when(carModelService.findById(eq(id), any())).thenReturn(Optional.of(model));
    when(carTypeService.findById(eq(request.getCarTypeId()), any())).thenReturn(Optional.ofNullable(type));
    when(carBrandService.findById(eq(id), any())).thenReturn(Optional.of(brand));
    when(carModelService.findIdByName(eq(request.getName()), any())).thenReturn(Optional.of(12L));
    ErrorResponse errorResponse = new ErrorResponse(400, 402, "CarModel with param: Q5 already exists");
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_ValidRequest_UpdatesCarModel() throws Exception {
    CarModel model = CarModelFixture.createCarModel();
    CarType type = CarTypeFixture.createCarType();
    CarBrand brand = CarBrandFixture.createCarBrand();
    CarModelRequest request = CarModelRequestFixture.createCarModelRequest();
    CarModelResponse response = CarModelResponse.fromEntity(model);
    long id = 1L;
    when(carModelService.findById(eq(id), any())).thenReturn(Optional.of(model));
    when(carBrandService.findById(eq(request.getCarBrandId()), any())).thenReturn(Optional.ofNullable(brand));
    when(carTypeService.findById(eq(request.getCarTypeId()), any())).thenReturn(Optional.ofNullable(type));
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
    CarModelRequest request = CarModelRequestFixture.createCarModelRequest();
    long id = 1L;
    when(carModelService.findById(eq(id), any())).thenReturn(Optional.empty());
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
    CarModel model = CarModelFixture.createCarModel();
    long id = 1L;
    when(carModelService.findById(eq(id), any())).thenReturn(Optional.of(model));
    this.mockMvc.perform(auth(delete(URL + "/" + id)))
        .andExpect(status().is2xxSuccessful());
  }
}
