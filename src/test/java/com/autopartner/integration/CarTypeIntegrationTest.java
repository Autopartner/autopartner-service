package com.autopartner.integration;

import com.autopartner.api.dto.request.CarTypeRequest;
import com.autopartner.api.dto.request.CarTypeRequestFixture;
import com.autopartner.api.dto.request.TaskCategoryRequest;
import com.autopartner.api.dto.request.TaskCategoryRequestFixture;
import com.autopartner.domain.CarType;
import com.autopartner.domain.CarTypeFixture;
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
import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarTypeIntegrationTest extends AbstractIntegrationTest {
  public static final String CAR_TYPE_URL = "/api/v1/car-types";
  @Autowired
  CarTypeRepository carTypeRepository;

  @Override
  @BeforeEach
  void setUp() throws Exception {
    super.setUp();
    carTypeRepository.deleteAll();
  }

  @Test
  void givenCarTypeRequest_whenCreateCarType_thenReturnSavedCarType() throws Exception {
    CarTypeRequest request = CarTypeRequestFixture.createCarTypeRequest();

    ResultActions result = mockMvc.perform(post(CAR_TYPE_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    result
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.name",
            is(request.getName())));
  }

  @Test
  void givenDuplicatedCarTypeName_whenCreateCarType_thenReturn400() throws Exception {

    carTypeRepository.save(CarTypeFixture.createCarType());
    CarTypeRequest request = CarTypeRequestFixture.createCarTypeRequest();

    ResultActions result = mockMvc.perform(post(CAR_TYPE_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    result
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", is("CarType with param: Sedan already exists")));
  }

  @Test
  public void givenListOfTCarTypes_whenGetAllCarTypes_thenReturnCarTypes() throws Exception {

    List<CarType> types = new ArrayList<>();
    types.add(CarTypeFixture.createCarType());
    types.add(CarTypeFixture.createCarTypeWithDifferentName());
    carTypeRepository.saveAll(types);

    ResultActions response = mockMvc.perform(get(CAR_TYPE_URL)
        .header(HttpHeaders.AUTHORIZATION, token));

    response.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.size()",
            is(types.size())));
  }

  @Test
  public void givenCarTypeId_whenGetCarTypeById_thenReturnCarTypeResponse() throws Exception {

    CarType type = carTypeRepository.save(CarTypeFixture.createCarType());

    ResultActions response = mockMvc.perform(get(CAR_TYPE_URL + "/{id}", type.getId())
        .header(HttpHeaders.AUTHORIZATION, token));

    response
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(type.getName())));

  }

  @Test
  public void givenInvalidCarTypeId_whenGetCarTypeById_thenReturn404() throws Exception {

    CarType type = carTypeRepository.save(CarTypeFixture.createCarType());
    long id = type.getId() + 100;

    ResultActions response = mockMvc.perform(get(CAR_TYPE_URL + "/{id}", id)
        .header(HttpHeaders.AUTHORIZATION, token));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("CarType with id=" + id + " is not found")));
  }

  @Test
  public void givenUpdatedCarTypeRequest_whenUpdateCarType_thenReturnUpdateCarTypeResponse() throws Exception {

    CarType type = carTypeRepository.save(CarTypeFixture.createCarType());
    carTypeRepository.save(type);

    TaskCategoryRequest request = TaskCategoryRequest.builder()
        .name("Re Name")
        .build();

    ResultActions response = mockMvc.perform(put(CAR_TYPE_URL + "/{id}", type.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    response
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(request.getName())));
  }

  @Test
  public void givenInvalidCarTypeId_whenUpdateCarType_thenReturn404() throws Exception {

    CarType type = carTypeRepository.save(CarTypeFixture.createCarType());
    long id = type.getId() + 100;
    TaskCategoryRequest request = TaskCategoryRequestFixture.createTaskCategoryRequest();

    ResultActions response = mockMvc.perform(put(CAR_TYPE_URL + "/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("CarType with id=" + id + " is not found")));
  }

  @Test
  public void givenDuplicatedCarTypeName_whenUpdateCarType_thenReturn400() throws Exception {

    CarType type1 = carTypeRepository.save(CarTypeFixture.createCarType());
    CarType type2 = carTypeRepository.save(CarTypeFixture.createCarTypeWithDifferentName());
    TaskCategoryRequest request = TaskCategoryRequestFixture.createTaskCategoryRequest().withName(type2.getName());

    ResultActions response = mockMvc.perform(put(CAR_TYPE_URL + "/{id}", type1.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("CarType with param: hatchback already exists")));
  }

  @Test
  void givenCarTypeId_whenDeleteCarType_thenReturn200() throws Exception {

    CarType type = carTypeRepository.save(CarTypeFixture.createCarType());

    ResultActions result = mockMvc.perform(delete(CAR_TYPE_URL + "/{id}", type.getId())
        .header(HttpHeaders.AUTHORIZATION, token));

    result
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void givenInvalidCarTypeId_whenDeleteCarType_thenReturn404() throws Exception {

    CarType type = carTypeRepository.save(CarTypeFixture.createCarType());
    long id = type.getId() + 100;

    ResultActions response = mockMvc.perform(delete(CAR_TYPE_URL + "/{id}", id)
        .header(HttpHeaders.AUTHORIZATION, token));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("CarType with id=" + id + " is not found")));
  }
}
