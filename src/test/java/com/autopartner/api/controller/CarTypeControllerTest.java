package com.autopartner.api.controller;

import com.autopartner.api.dto.request.CarTypeRequest;
import com.autopartner.api.dto.request.CarTypeRequestFixture;
import com.autopartner.api.dto.response.CarTypeResponse;
import com.autopartner.api.dto.response.ErrorResponse;
import com.autopartner.domain.CarType;
import com.autopartner.domain.CarTypeFixture;
import com.autopartner.service.CarTypeService;
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

@WebMvcTest(controllers = CarTypeController.class)
@AutoConfigureMockMvc
public class CarTypeControllerTest extends AbstractControllerTest {

  public static final String URL = "/api/v1/car-types";

  @MockBean
  CarTypeService carTypeService;

  @Test
  void getAll_NotAuthorized_ReturnsClientError() throws Exception {
    this.mockMvc.perform(get(URL))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(UNAUTHORIZED_RESPONSE)));
  }

  @Test
  void getAll_Authorized_ReturnsCarTypes() throws Exception {
    CarType type = CarTypeFixture.createCarType();
    CarTypeResponse response = CarTypeResponse.fromEntity(type);
    List<CarTypeResponse> responses = List.of(response);
    when(carTypeService.findAll()).thenReturn(List.of(type));
    this.mockMvc.perform(auth(get(URL)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(responses)));
  }

  @Test
  void get_ValidCompanyId_ReturnsCarType() throws Exception {
    CarType type = CarTypeFixture.createCarType();
    CarTypeResponse response = CarTypeResponse.fromEntity(type);
    long id = 1L;
    when(carTypeService.findById(id)).thenReturn(Optional.of(type));
    this.mockMvc.perform(auth(get(URL + "/" + id)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void get_InvalidCarTypeId_ReturnsError() throws Exception {
    long id = 1L;
    when(carTypeService.findById(5L)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "CarType with id=1 is not found");
    this.mockMvc.perform(auth(get(URL + "/" + id)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void create_CarTypeAlreadyExists_ReturnsError() throws Exception {
    CarTypeRequest request = CarTypeRequestFixture.createCarTypeRequest();
    when(carTypeService.existsByName(request.getName())).thenReturn(true);
    ErrorResponse errorResponse = new ErrorResponse(400, 402, "CarType with param: Sedan already exists");
    this.mockMvc.perform(auth(post(URL))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void create_ValidRequest_CreatesCarType() throws Exception {
    CarType type = CarTypeFixture.createCarType();
    CarTypeRequest request = CarTypeRequestFixture.createCarTypeRequest();
    CarTypeResponse response = CarTypeResponse.fromEntity(type);
    when(carTypeService.existsByName(request.getName())).thenReturn(false);
    when(carTypeService.create(request, type.getCompanyId())).thenReturn(type);
    this.mockMvc.perform(auth(post(URL))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void update_InvalidCarTypeId_ReturnsError() throws Exception {
    CarTypeRequest request = CarTypeRequestFixture.createCarTypeRequest();
    long id = 1L;
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
  void update_ValidRequest_UpdatesCarType() throws Exception {
    CarType type = CarTypeFixture.createCarType();
    CarTypeRequest request = CarTypeRequestFixture.createCarTypeRequest();
    CarTypeResponse response = CarTypeResponse.fromEntity(type);
    long id = 1L;
    when(carTypeService.findById(id)).thenReturn(Optional.of(type));
    when(carTypeService.update(type, request)).thenReturn(type);
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void delete_InvalidCarTypeId_ReturnsError() throws Exception {
    CarTypeRequest request = CarTypeRequestFixture.createCarTypeRequest();
    long id = 1L;
    when(carTypeService.findById(id)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "CarType with id=1 is not found");
    this.mockMvc.perform(auth(delete(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void delete_ValidRequest_DeletesCarType() throws Exception {
    CarType type = CarTypeFixture.createCarType();
    long id = 1L;
    when(carTypeService.findById(id)).thenReturn(Optional.of(type));
    this.mockMvc.perform(auth(delete(URL + "/" + id)))
        .andExpect(status().is2xxSuccessful());
  }
}
