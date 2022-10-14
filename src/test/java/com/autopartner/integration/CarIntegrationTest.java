package com.autopartner.integration;

import com.autopartner.api.dto.request.CarRequest;
import com.autopartner.api.dto.request.CarRequestFixture;
import com.autopartner.domain.*;
import com.autopartner.repository.CarBrandRepository;
import com.autopartner.repository.CarModelRepository;
import com.autopartner.repository.CarRepository;
import com.autopartner.repository.CarTypeRepository;
import com.autopartner.repository.ClientRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarIntegrationTest extends AbstractIntegrationTest {
  public static final String CAR_MODEL_URL = "/api/v1/cars";
  @Autowired
  CarModelRepository carModelRepository;
  @Autowired
  ClientRepository clientRepository;
  @Autowired
  CarBrandRepository carBrandRepository;
  @Autowired
  CarTypeRepository carTypeRepository;
  @Autowired
  CarRepository carRepository;


  @Override
  @BeforeEach
  void setUp() throws Exception {
    super.setUp();
    carRepository.deleteAll();
  }

  @Test
  void givenCarRequest_whenCreateCar_thenReturnSavedCar() throws Exception {

    CarRequest request = CarRequestFixture.createCarRequest();
    CarBrand brand = CarBrandFixture.createCarBrand();
    carBrandRepository.save(brand);
    CarType type = CarTypeFixture.createCarType();
    carTypeRepository.save(type);
    CarModel model = CarModelFixture.createCarModel(brand, type);
    carModelRepository.save(model);
    Client client = ClientFixture.createPersonClient();
    clientRepository.save(client);

    ResultActions result = mockMvc.perform(post(CAR_MODEL_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    result
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.plateNumber", is(request.getPlateNumber())))
        .andExpect(jsonPath("$.manufactureYear", is(request.getManufactureYear().toString())))
        .andExpect(jsonPath("$.vinCode", is(request.getVinCode())))
        .andExpect(jsonPath("$.note", is(request.getNote())));
  }

  @Test
  void givenDuplicatedCarName_whenCreateCar_thenReturn400() throws Exception {

    CarBrand brand = CarBrandFixture.createCarBrand();
    carBrandRepository.save(brand);
    CarType type = CarTypeFixture.createCarType();
    carTypeRepository.save(type);
    CarModel model = CarModelFixture.createCarModel(brand, type);
    carModelRepository.save(model);
    Client client = ClientFixture.createPersonClient();
    clientRepository.save(client);
    Car car = CarFixture.createCar();
    carRepository.save(car);

    CarRequest request = CarRequestFixture.createCarRequest();

    ResultActions result = mockMvc.perform(post(CAR_MODEL_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    result
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", is("Car with param: Aa1111 already exists")));
  }

  @Test
  public void givenListOfTCars_whenGetAllCars_thenReturnCars() throws Exception {

    CarBrand brand = CarBrandFixture.createCarBrand();
    carBrandRepository.save(brand);
    CarType type = CarTypeFixture.createCarType();
    carTypeRepository.save(type);
    CarModel model = CarModelFixture.createCarModel(brand, type);
    carModelRepository.save(model);
    Client client = ClientFixture.createPersonClient();
    clientRepository.save(client);

    List<Car> cars = new ArrayList<>();
    cars.add(CarFixture.createCar());
    cars.add(CarFixture.createCarWithDifferentWinCode());
    carRepository.saveAll(cars);

    ResultActions response = mockMvc.perform(get(CAR_MODEL_URL)
        .header(HttpHeaders.AUTHORIZATION, token));

    response.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.size()",
            is(cars.size())));
  }

  @Test
  public void givenCarId_whenGetCarById_thenReturnCarResponse() throws Exception {
    CarBrand brand = CarBrandFixture.createCarBrand();
    carBrandRepository.save(brand);
    CarType type = CarTypeFixture.createCarType();
    carTypeRepository.save(type);
    CarModel model = CarModelFixture.createCarModel(brand, type);
    carModelRepository.save(model);
    Client client = ClientFixture.createPersonClient();
    clientRepository.save(client);
    Car car = carRepository.save(CarFixture.createCar());

    ResultActions response = mockMvc.perform(get(CAR_MODEL_URL + "/{id}", car.getId())
        .header(HttpHeaders.AUTHORIZATION, token));

    response
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.plateNumber", is(car.getPlateNumber())))
        .andExpect(jsonPath("$.manufactureYear", is(car.getManufactureYear().toString())))
        .andExpect(jsonPath("$.vinCode", is(car.getVinCode())))
        .andExpect(jsonPath("$.note", is(car.getNote())));
  }

  @Test
  public void givenInvalidCarId_whenGetCarById_thenReturn404() throws Exception {
    CarBrand brand = CarBrandFixture.createCarBrand();
    carBrandRepository.save(brand);
    CarType type = CarTypeFixture.createCarType();
    carTypeRepository.save(type);
    CarModel model = CarModelFixture.createCarModel(brand, type);
    carModelRepository.save(model);
    Client client = ClientFixture.createPersonClient();
    clientRepository.save(client);
    Car car = carRepository.save(CarFixture.createCar());

    long id = car.getId() + 100;

    ResultActions response = mockMvc.perform(get(CAR_MODEL_URL + "/{id}", id)
        .header(HttpHeaders.AUTHORIZATION, token));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("Car with id=" + id + " is not found")));
  }

  @Test
  public void givenUpdatedCarRequest_whenUpdateCar_thenReturnUpdateCarResponse() throws Exception {

    CarBrand brand = CarBrandFixture.createCarBrand();
    carBrandRepository.save(brand);
    CarType type = CarTypeFixture.createCarType();
    carTypeRepository.save(type);
    CarModel model = CarModelFixture.createCarModel(brand, type);
    carModelRepository.save(model);
    Client client = ClientFixture.createPersonClient();
    clientRepository.save(client);
    Car car = carRepository.save(CarFixture.createCar());

    CarRequest request = CarRequest.builder()
        .vinCode("Aa1221")
        .plateNumber("BH222AA")
        .note("New note")
        .manufactureYear(Year.of(2019))
        .carModelId(model.getId())
        .clientId(client.getId())
        .build();

    ResultActions response = mockMvc.perform(put(CAR_MODEL_URL + "/{id}", car.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    response
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.plateNumber", is(request.getPlateNumber())))
        .andExpect(jsonPath("$.manufactureYear", is(request.getManufactureYear().toString())))
        .andExpect(jsonPath("$.vinCode", is(request.getVinCode())))
        .andExpect(jsonPath("$.note", is(request.getNote())));
  }

  @Test
  public void givenInvalidCarId_whenUpdateCar_thenReturn404() throws Exception {

    CarBrand brand = CarBrandFixture.createCarBrand();
    carBrandRepository.save(brand);
    CarType type = CarTypeFixture.createCarType();
    carTypeRepository.save(type);
    CarModel model = CarModelFixture.createCarModel(brand, type);
    carModelRepository.save(model);
    Client client = ClientFixture.createPersonClient();
    clientRepository.save(client);
    Car car = carRepository.save(CarFixture.createCar());

    long id = car.getId() + 100;
    CarRequest request = CarRequestFixture.createCarRequest();

    ResultActions response = mockMvc.perform(put(CAR_MODEL_URL + "/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("Car with id=" + id + " is not found")));
  }

  @Test
  public void givenDuplicatedCarName_whenUpdateCar_thenReturn400() throws Exception {

    CarBrand brand = CarBrandFixture.createCarBrand();
    carBrandRepository.save(brand);
    CarType type = CarTypeFixture.createCarType();
    carTypeRepository.save(type);
    CarModel model = CarModelFixture.createCarModel(brand, type);
    carModelRepository.save(model);
    Client client = ClientFixture.createPersonClient();
    clientRepository.save(client);

    Car car1 = carRepository.save(CarFixture.createCar());
    Car car2 = carRepository.save(CarFixture.createCarWithDifferentWinCode());

    CarRequest request = CarRequestFixture.createCarRequest().withVinCode(car2.getVinCode());

    ResultActions response = mockMvc.perform(put(CAR_MODEL_URL + "/{id}", car1.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("Car with param: Bb2222 already exists")));
  }

  @Test
  void givenCarId_whenDeleteCar_thenReturn200() throws Exception {

    CarBrand brand = CarBrandFixture.createCarBrand();
    carBrandRepository.save(brand);
    CarType type = CarTypeFixture.createCarType();
    carTypeRepository.save(type);
    CarModel model = CarModelFixture.createCarModel(brand, type);
    carModelRepository.save(model);
    Client client = ClientFixture.createPersonClient();
    clientRepository.save(client);

    Car car = carRepository.save(CarFixture.createCar());

    ResultActions result = mockMvc.perform(delete(CAR_MODEL_URL + "/{id}", car.getId())
        .header(HttpHeaders.AUTHORIZATION, token));

    result
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void givenInvalidCarId_whenDeleteCar_thenReturn404() throws Exception {

    CarBrand brand = CarBrandFixture.createCarBrand();
    carBrandRepository.save(brand);
    CarType type = CarTypeFixture.createCarType();
    carTypeRepository.save(type);
    CarModel model = CarModelFixture.createCarModel(brand, type);
    carModelRepository.save(model);
    Client client = ClientFixture.createPersonClient();
    clientRepository.save(client);

    Car car = carRepository.save(CarFixture.createCar());
    long id = car.getId() + 100;

    ResultActions response = mockMvc.perform(delete(CAR_MODEL_URL + "/{id}", id)
        .header(HttpHeaders.AUTHORIZATION, token));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("Car with id=" + id + " is not found")));
  }
}
