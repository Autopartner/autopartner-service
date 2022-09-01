package com.autopartner.service;

import com.autopartner.api.dto.request.CarModelRequest;
import com.autopartner.api.dto.request.CarModelRequestFixture;
import com.autopartner.domain.*;
import com.autopartner.repository.CarModelRepository;
import com.autopartner.service.impl.CarModelServiceImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PACKAGE)
public class CarModelServiceTest {

  @Mock
  CarModelRepository carModelRepository;
  @InjectMocks
  CarModelServiceImpl carModelService;
  @Captor
  ArgumentCaptor<CarModel> carModelArgumentCaptor;
  @Captor
  ArgumentCaptor<Long> longArgumentCaptor;
  List<CarModel> models;
  CarModel model;
  CarBrand brand;
  CarType type;
  CarModelRequest request;
  Long id;

  @BeforeEach
  public void init() {
    brand = CarBrandFixture.createCarBrand();
    type = CarTypeFixture.createCarType();
    model = CarModelFixture.createCarModel();
    models = List.of(model);
    request = CarModelRequestFixture.createCarModelRequest();
    id = 1L;
  }

  @Test
  void findAll() {
    when(carModelRepository.findByActiveTrue()).thenReturn(models);
    List<CarModel> actualCarBrands = carModelService.findAll();
    assertThat(models).isEqualTo(actualCarBrands);
  }

  @Test
  void create() {
    carModelService.create(request, brand, type, id);
    verify(carModelRepository).save(carModelArgumentCaptor.capture());
    CarModel actualCarModel = carModelArgumentCaptor.getValue();
    assertThat(actualCarModel.getName()).isEqualTo((request.getName()));
    assertThat(actualCarModel.getCarBrand().getId()).isEqualTo(request.getCarBrandId());
    assertThat(actualCarModel.getCarType().getId()).isEqualTo(request.getCarTypeId());
  }

  @Test
  void findById() {
    when(carModelRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.ofNullable(model));
    carModelService.findById(model.getId());
    verify(carModelRepository).findByIdAndActiveTrue(longArgumentCaptor.capture());
    id = longArgumentCaptor.getValue();
    assertThat(id).isEqualTo(model.getId());
  }

  @Test
  void update() {
    carModelService.update(model, brand, type, request);
    verify(carModelRepository).save(carModelArgumentCaptor.capture());
    CarModel actualCarModel = carModelArgumentCaptor.getValue();
    assertThat(actualCarModel.getName()).isEqualTo((request.getName()));
    assertThat(actualCarModel.getCarBrand().getId()).isEqualTo(request.getCarBrandId());
    assertThat(actualCarModel.getCarType().getId()).isEqualTo(request.getCarTypeId());
  }

  @Test
  void delete() {
    carModelService.delete(model);
    verify(carModelRepository).save(carModelArgumentCaptor.capture());
    CarModel actualCarModel = carModelArgumentCaptor.getValue();
    assertThat(actualCarModel).isEqualTo(model);
    assertThat(actualCarModel.getActive()).isFalse();
  }
}
