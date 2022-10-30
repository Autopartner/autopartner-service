package com.autopartner.integration;

import com.autopartner.api.dto.request.CarBrandRequest;
import com.autopartner.api.dto.request.CarBrandRequestFixture;
import com.autopartner.api.dto.request.TaskCategoryRequest;
import com.autopartner.api.dto.request.TaskCategoryRequestFixture;
import com.autopartner.domain.CarBrand;
import com.autopartner.domain.CarBrandFixture;
import com.autopartner.repository.CarBrandRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarBrandIntegrationTest extends AbstractIntegrationTest {
  public static final String CAR_BRAND_URL = "/api/v1/car-brands";
  @Autowired
  CarBrandRepository carBrandRepository;

  @Override
  @BeforeEach
  void setUp() throws Exception {
    super.setUp();
    carBrandRepository.deleteAll();
  }

  @Test
  void givenCarBrandRequest_whenCreateCarBrand_thenReturnSavedCarBrand() throws Exception {
    CarBrandRequest request = CarBrandRequestFixture.createCarBrandRequest();

    ResultActions result = mockMvc.perform(post(CAR_BRAND_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    result
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.name",
            is(request.getName())));
  }

  @Test
  void givenDuplicatedCarBrandName_whenCreateCarBrand_thenReturn400() throws Exception {

    carBrandRepository.save(CarBrandFixture.createCarBrand());
    CarBrandRequest request = CarBrandRequestFixture.createCarBrandRequest();

    ResultActions result = mockMvc.perform(post(CAR_BRAND_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    result
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", is("CarBrand with param: Audi already exists")));
  }

  @Test
  public void givenListOfTCarBrands_whenGetAllCarBrands_thenReturnCarBrands() throws Exception {

    List<CarBrand> brands = new ArrayList<>();
    brands.add(CarBrandFixture.createCarBrand());
    brands.add(CarBrandFixture.createCarBrandWithDifferentName());
    carBrandRepository.saveAll(brands);

    ResultActions response = mockMvc.perform(get(CAR_BRAND_URL)
        .header(HttpHeaders.AUTHORIZATION, token));

    response.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.size()",
            is(brands.size())));
  }

  @Test
  public void givenCarBrandId_whenGetCarBrandById_thenReturnCarBrandResponse() throws Exception {

    CarBrand brand = carBrandRepository.save(CarBrandFixture.createCarBrand());

    ResultActions response = mockMvc.perform(get(CAR_BRAND_URL + "/{id}", brand.getId())
        .header(HttpHeaders.AUTHORIZATION, token));

    response
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(brand.getName())));

  }

  @Test
  public void givenInvalidCarBrandId_whenGetCarBrandById_thenReturn404() throws Exception {

    CarBrand brand = carBrandRepository.save(CarBrandFixture.createCarBrand());
    long id = brand.getId() + 100;

    ResultActions response = mockMvc.perform(get(CAR_BRAND_URL + "/{id}", id)
        .header(HttpHeaders.AUTHORIZATION, token));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("CarBrand with id=" + id + " is not found")));
  }

  @Test
  public void givenUpdatedCarBrandRequest_whenUpdateCarBrand_thenReturnUpdateCarBrandResponse() throws Exception{

    CarBrand brand = carBrandRepository.save(CarBrandFixture.createCarBrand());
    carBrandRepository.save(brand);

    TaskCategoryRequest request = TaskCategoryRequest.builder()
        .name("Re Name")
        .build();

    ResultActions response = mockMvc.perform(put(CAR_BRAND_URL + "/{id}", brand.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    response
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(request.getName())));
  }

  @Test
  public void givenInvalidCarBrandId_whenUpdateCarBrand_thenReturn404() throws Exception{

    CarBrand brand = carBrandRepository.save(CarBrandFixture.createCarBrand());
    long id = brand.getId() + 100;
    TaskCategoryRequest request = TaskCategoryRequestFixture.createTaskCategoryRequest();

    ResultActions response = mockMvc.perform(put(CAR_BRAND_URL + "/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("CarBrand with id=" + id +" is not found")));
  }

  @Test
  public void givenDuplicatedCarBrandName_whenUpdateCarBrand_thenReturn400() throws Exception{

    CarBrand brand1 = carBrandRepository.save(CarBrandFixture.createCarBrand());
    CarBrand brand2 = carBrandRepository.save(CarBrandFixture.createCarBrandWithDifferentName());
    TaskCategoryRequest request = TaskCategoryRequestFixture.createTaskCategoryRequest().withName(brand2.getName());

    ResultActions response = mockMvc.perform(put(CAR_BRAND_URL + "/{id}", brand1.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("CarBrand with param: BMW already exists")));
  }

  @Test
  void givenCarBrandId_whenDeleteCarBrand_thenReturn200() throws Exception {

    CarBrand brand = carBrandRepository.save(CarBrandFixture.createCarBrand());

    ResultActions result = mockMvc.perform(delete(CAR_BRAND_URL + "/{id}", brand.getId())
        .header(HttpHeaders.AUTHORIZATION, token));

    result
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void givenInvalidCarBrandId_whenDeleteCarBrand_thenReturn404() throws Exception{

    CarBrand brand = carBrandRepository.save(CarBrandFixture.createCarBrand());
    long id = brand.getId() + 100;

    ResultActions response = mockMvc.perform(delete(CAR_BRAND_URL + "/{id}", id)
        .header(HttpHeaders.AUTHORIZATION, token));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("CarBrand with id=" + id +" is not found")));
  }
}
