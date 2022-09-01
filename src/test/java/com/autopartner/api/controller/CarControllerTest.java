package com.autopartner.api.controller;

import com.autopartner.api.dto.request.CarRequest;
import com.autopartner.api.dto.request.CarRequestFixture;
import com.autopartner.api.dto.response.*;
import com.autopartner.domain.*;
import com.autopartner.service.CarModelService;
import com.autopartner.service.CarService;
import com.autopartner.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.autopartner.api.configuration.WebConfiguration.UNAUTHORIZED_RESPONSE;
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
  @MockBean
  ClientService clientService;
  @MockBean
  CarModelService carModelService;

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
    long id = 1L;
    when(carService.findById(id)).thenReturn(Optional.ofNullable(car));
    CarResponse carResponse = CarResponse.fromEntity(Objects.requireNonNull(car));
    this.mockMvc.perform(auth(get(URL + "/" + id)))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().string(objectMapper.writeValueAsString(carResponse)));
  }

  @Test
  void get_InvalidCarId_ReturnsError() throws Exception {
    long id = 1L;
    when(carService.findById(id)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "Car with id=1 is not found");
    this.mockMvc.perform(auth(get(URL + "/" + id)))
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void create_ValidRequest_CreatesCar() throws Exception {
    Car car = CarFixture.createCar();
    CarModel model = CarModelFixture.createCarModel();
    Client client = ClientFixture.createClient();
    CarRequest request = CarRequestFixture.createCarRequest();
    CarResponse response = CarResponse.fromEntity(car);
    when(clientService.findById(request.getClientId())).thenReturn(Optional.ofNullable(client));
    when(carModelService.findById(request.getCarModelId())).thenReturn(Optional.ofNullable(model));
    when(carService.create(request, client, model, car.getCompanyId())).thenReturn(car);
    this.mockMvc.perform(auth(post(URL))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void update_InvalidCarId_ReturnsError() throws Exception {
    CarRequest request = CarRequestFixture.createCarRequest();
    long id = 1L;
    when(carService.findById(id)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "Car with id=1 is not found");
    this.mockMvc.perform(auth(put(URL + "/" + id))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_ValidRequest_UpdatesCar() throws Exception {
    Car car = CarFixture.createCar();
    CarModel model = CarModelFixture.createCarModel();
    Client client = ClientFixture.createClient();
    CarRequest request = CarRequestFixture.createCarRequest();
    CarResponse response = CarResponse.fromEntity(car);
    long id = 1L;
    when(clientService.findById(request.getClientId())).thenReturn(Optional.ofNullable(client));
    when(carModelService.findById(request.getCarModelId())).thenReturn(Optional.ofNullable(model));
    when(carService.findById(id)).thenReturn(Optional.of(car));
    when(carService.update(car, client, model, request)).thenReturn(car);
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void delete_InvalidCarId_ReturnsError() throws Exception {
    CarRequest request = CarRequestFixture.createCarRequest();
    long id = 1L;
    when(carService.findById(id)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "Car with id=1 is not found");
    this.mockMvc.perform(auth(delete(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void delete_ValidRequest_DeletesCar() throws Exception {
    Car car = CarFixture.createCar();
    long id = 1L;
    when(carService.findById(id)).thenReturn(Optional.of(car));
    this.mockMvc.perform(auth(delete(URL + "/" + id)))
            .andExpect(status().is2xxSuccessful());
  }
}
