package com.autopartner.integration;

import com.autopartner.api.dto.request.CarModelRequest;
import com.autopartner.api.dto.request.CarModelRequestFixture;
import com.autopartner.domain.CarBrand;
import com.autopartner.domain.CarBrandFixture;
import com.autopartner.domain.CarModel;
import com.autopartner.domain.CarModelFixture;
import com.autopartner.domain.CarType;
import com.autopartner.domain.CarTypeFixture;
import com.autopartner.repository.CarBrandRepository;
import com.autopartner.repository.CarModelRepository;
import com.autopartner.repository.CarTypeRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarModelIntegrationTest extends AbstractIntegrationTest {
  public static final String CAR_MODEL_URL = "/api/v1/car-models";
  @Autowired
  CarModelRepository carModelRepository;
  @Autowired
  CarBrandRepository carBrandRepository;
  @Autowired
  CarTypeRepository carTypeRepository;

  @Override
  @BeforeEach
  void setUp() throws Exception {
    super.setUp();
    carModelRepository.deleteAll();
  }

  @Test
  void givenCarModelRequest_whenCreateCarModel_thenReturnSavedCarModel() throws Exception {

    CarModelRequest request = CarModelRequestFixture.createCarModelRequest();
    CarBrand brand = CarBrandFixture.createCarBrand();
    carBrandRepository.save(brand);
    CarType type = CarTypeFixture.createCarType();
    carTypeRepository.save(type);

    ResultActions result = mockMvc.perform(post(CAR_MODEL_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    result
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.name",
            is(request.getName())));
  }

  @Test
  void givenDuplicatedCarModelName_whenCreateCarModel_thenReturn400() throws Exception {

    CarBrand brand = CarBrandFixture.createCarBrand();
    carBrandRepository.save(brand);
    CarType type = CarTypeFixture.createCarType();
    carTypeRepository.save(type);

    carModelRepository.save(CarModelFixture.createCarModel(brand, type));

    CarModelRequest request = CarModelRequestFixture.createCarModelRequest();

    ResultActions result = mockMvc.perform(post(CAR_MODEL_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    result
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", is("CarModel with param: Q5 already exists")));
  }

  @Test
  public void givenListOfTCarModels_whenGetAllCarModels_thenReturnCarModels() throws Exception {

    CarBrand brand = CarBrandFixture.createCarBrand();
    carBrandRepository.save(brand);
    CarType type = CarTypeFixture.createCarType();
    carTypeRepository.save(type);

    List<CarModel> models = new ArrayList<>();
    models.add(CarModelFixture.createCarModel(brand, type));
    models.add(CarModelFixture.createCarModelWithDifferentName(brand, type));
    carModelRepository.saveAll(models);

    ResultActions response = mockMvc.perform(get(CAR_MODEL_URL)
        .header(HttpHeaders.AUTHORIZATION, token));

    response.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.size()",
            is(models.size())));
  }

  @Test
  public void givenCarModelId_whenGetCarModelById_thenReturnCarModelResponse() throws Exception {
    CarBrand brand = CarBrandFixture.createCarBrand();
    carBrandRepository.save(brand);
    CarType type = CarTypeFixture.createCarType();
    carTypeRepository.save(type);

    CarModel model = carModelRepository.save(CarModelFixture.createCarModel(brand, type));

    ResultActions response = mockMvc.perform(get(CAR_MODEL_URL + "/{id}", model.getId())
        .header(HttpHeaders.AUTHORIZATION, token));

    response
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(model.getName())));

  }

  @Test
  public void givenInvalidCarModelId_whenGetCarModelById_thenReturn404() throws Exception {

    CarBrand brand = CarBrandFixture.createCarBrand();
    carBrandRepository.save(brand);
    CarType type = CarTypeFixture.createCarType();
    carTypeRepository.save(type);

    CarModel model = carModelRepository.save(CarModelFixture.createCarModel(brand, type));
    long id = model.getId() + 100;

    ResultActions response = mockMvc.perform(get(CAR_MODEL_URL + "/{id}", id)
        .header(HttpHeaders.AUTHORIZATION, token));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("CarModel with id=" + id + " is not found")));
  }

  @Test
  public void givenUpdatedCarModelRequest_whenUpdateCarModel_thenReturnUpdateCarModelResponse() throws Exception {

    CarBrand brand = CarBrandFixture.createCarBrand();
    carBrandRepository.save(brand);
    CarType type = CarTypeFixture.createCarType();
    carTypeRepository.save(type);

    CarModel model = carModelRepository.save(CarModelFixture.createCarModel(brand, type));

    CarModelRequest request = CarModelRequest.builder()
        .name("Re Name")
        .carTypeId(type.getId())
        .carBrandId(brand.getId())
        .build();

    ResultActions response = mockMvc.perform(put(CAR_MODEL_URL + "/{id}", model.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    response
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(request.getName())));
  }

  @Test
  public void givenInvalidCarModelId_whenUpdateCarModel_thenReturn404() throws Exception {

    CarBrand brand = CarBrandFixture.createCarBrand();
    carBrandRepository.save(brand);
    CarType type = CarTypeFixture.createCarType();
    carTypeRepository.save(type);

    CarModel model = carModelRepository.save(CarModelFixture.createCarModel(brand, type));
    long id = model.getId() + 100;
    CarModelRequest request = CarModelRequestFixture.createCarModelRequest();

    ResultActions response = mockMvc.perform(put(CAR_MODEL_URL + "/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("CarModel with id=" + id + " is not found")));
  }

    @Test
  public void givenDuplicatedCarModelName_whenUpdateCarModel_thenReturn400() throws Exception {

    CarBrand brand = CarBrandFixture.createCarBrand();
    carBrandRepository.save(brand);
    CarType type = CarTypeFixture.createCarType();
    carTypeRepository.save(type);

    CarModel model1 = carModelRepository.save(CarModelFixture.createCarModel(brand, type));
    CarModel model2 = carModelRepository.save(CarModelFixture.createCarModelWithDifferentName(brand, type));

    CarModelRequest request = CarModelRequestFixture.createCarModelRequest().withName(model2.getName());

    ResultActions response = mockMvc.perform(put(CAR_MODEL_URL + "/{id}", model1.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("CarModel with param: Q6 already exists")));
  }

  @Test
  void givenCarModelId_whenDeleteCarModel_thenReturn200() throws Exception {

    CarBrand brand = CarBrandFixture.createCarBrand();
    carBrandRepository.save(brand);
    CarType type = CarTypeFixture.createCarType();
    carTypeRepository.save(type);

    CarModel model = carModelRepository.save(CarModelFixture.createCarModel(brand, type));

    ResultActions result = mockMvc.perform(delete(CAR_MODEL_URL + "/{id}", model.getId())
        .header(HttpHeaders.AUTHORIZATION, token));

    result
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void givenInvalidCarModelId_whenDeleteCarModel_thenReturn404() throws Exception {

    CarBrand brand = CarBrandFixture.createCarBrand();
    carBrandRepository.save(brand);
    CarType type = CarTypeFixture.createCarType();
    carTypeRepository.save(type);

    CarModel model = carModelRepository.save(CarModelFixture.createCarModel(brand, type));
    long id = model.getId() + 100;

    ResultActions response = mockMvc.perform(delete(CAR_MODEL_URL + "/{id}", id)
        .header(HttpHeaders.AUTHORIZATION, token));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("CarModel with id=" + id + " is not found")));
  }
}
