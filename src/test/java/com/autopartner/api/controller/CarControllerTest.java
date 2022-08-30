package com.autopartner.api.controller;

import com.autopartner.api.dto.request.CarRequest;
import com.autopartner.api.dto.response.CarResponse;
import com.autopartner.api.dto.response.ErrorResponse;
import com.autopartner.domain.Car;
import com.autopartner.domain.CarFixture;
import com.autopartner.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.autopartner.api.configuration.WebConfiguration.UNAUTHORIZED_RESPONSE;
import static com.autopartner.api.dto.request.CarRequestFixture.createCarRequest;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CarController.class)
@AutoConfigureMockMvc
public class CarControllerTest extends AbstractControllerTest {

  public static final String URL = "/api/v1/cars";

  @MockBean
  CarService carService;

  @Test
  void getAll_NotAuthorized_ReturnsClientError() throws Exception {
    this.mockMvc.perform(get(URL))
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(objectMapper.writeValueAsString(UNAUTHORIZED_RESPONSE)));
  }

  @Test
  void getAll_Authorized_ReturnsCars() throws Exception {
    Car car = CarFixture.createCar();
    List<CarResponse> carResponses = List.of(CarResponse.fromEntity(car));
    when(carService.findAll()).thenReturn(List.of(car));
    this.mockMvc.perform(auth(get(URL)))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().string(objectMapper.writeValueAsString(carResponses)));
  }

  @Test
  void get_ValidCarId_ReturnsCar() throws Exception {
    Car car = CarFixture.createCar();
    long carId = 1L;
    when(carService.findById(carId)).thenReturn(Optional.ofNullable(car));
    CarResponse carResponse = CarResponse.fromEntity(Objects.requireNonNull(car));
    this.mockMvc.perform(auth(get(URL + "/" + carId)))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().string(objectMapper.writeValueAsString(carResponse)));
  }

  @Test
  void get_InvalidCarId_ReturnsError() throws Exception {
    long carId = 1L;
    when(carService.findById(carId)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "Car with id=1 is not found");
    this.mockMvc.perform(auth(get(URL + "/" + carId)))
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void create_ValidRequest_CreatesCar() throws Exception {
    CarRequest request = createCarRequest();
    Car car = CarFixture.createCar();
    CarResponse response = CarResponse.fromEntity(car);
    when(carService.create(request, car.getCompanyId())).thenReturn(car);
    this.mockMvc.perform(auth(post(URL))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void update_InvalidCarId_ReturnsError() throws Exception {
    CarRequest request = createCarRequest();
    long carId = 1L;
    when(carService.findById(carId)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "Car with id=1 is not found");
    this.mockMvc.perform(auth(put(URL + "/" + carId))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_ValidRequest_UpdatesCar() throws Exception {
    CarRequest request = createCarRequest();
    Car car = CarFixture.createCar();
    long carId = 1L;
    CarResponse response = CarResponse.fromEntity(car);
    when(carService.findById(carId)).thenReturn(Optional.of(car));
    when(carService.update(car, request)).thenReturn(car);
    this.mockMvc.perform(auth(put(URL + "/" + carId))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void delete_InvalidCarId_ReturnsError() throws Exception {
    CarRequest request = createCarRequest();
    long carId = 1L;
    when(carService.findById(carId)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "Car with id=1 is not found");
    this.mockMvc.perform(auth(delete(URL + "/" + carId))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void delete_ValidRequest_DeletesCar() throws Exception {
    Car car = CarFixture.createCar();
    long carId = 1L;
    when(carService.findById(carId)).thenReturn(Optional.of(car));
    this.mockMvc.perform(auth(delete(URL + "/" + carId)))
            .andExpect(status().is2xxSuccessful());
  }
}
