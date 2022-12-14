package com.autopartner.service;

import com.autopartner.api.dto.request.CarModelRequest;
import com.autopartner.api.dto.request.CarModelRequestFixture;
import com.autopartner.domain.*;
import com.autopartner.repository.CarModelRepository;
import com.autopartner.service.impl.CarModelServiceImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
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
  ArgumentCaptor<Long> carModelIdArgumentCaptor;
  @Captor
  ArgumentCaptor<Long> companyIdArgumentCaptor;

  @Test
  void findAll() {
    Long companyId = 1L;
    List<CarModel> models = List.of(CarModelFixture.createCarModel());
    when(carModelRepository.findAll(companyId)).thenReturn(models);
    List<CarModel> actualCarBrands = carModelService.findAll(companyId);
    assertThat(models).isEqualTo(actualCarBrands);
  }

  @Test
  void create() {
    CarModelRequest request = CarModelRequestFixture.createCarModelRequest();
    CarBrand brand = CarBrandFixture.createCarBrandWithDifferentName();
    CarType type = CarTypeFixture.createCarTypeWithDifferentName();
    long id = 1L;
    carModelService.create(request, brand, type, id);
    verify(carModelRepository).save(carModelArgumentCaptor.capture());
    CarModel actualCarModel = carModelArgumentCaptor.getValue();
    assertThat(actualCarModel.getName()).isEqualTo((request.getName()));
    assertThat(actualCarModel.getCarBrand().getId()).isEqualTo(request.getCarBrandId());
    assertThat(actualCarModel.getCarType().getId()).isEqualTo(request.getCarTypeId());
  }

  @Test
  void findById() {
    Long companyId = 1L;
    CarModel model = CarModelFixture.createCarModel();
    when(carModelRepository.findById(anyLong(), anyLong())).thenReturn(Optional.ofNullable(model));
    carModelService.findById(model.getId(), companyId);
    verify(carModelRepository).findById(carModelIdArgumentCaptor.capture(), companyIdArgumentCaptor.capture());
    long id = carModelIdArgumentCaptor.getValue();
    long currentCompanyId = companyIdArgumentCaptor.getValue();
    assertThat(id).isEqualTo(model.getId());
    assertThat(currentCompanyId).isEqualTo(companyId);
  }

  @Test
  void update() {
    CarModel model = CarModelFixture.createCarModel();
    CarModelRequest request = CarModelRequestFixture.createCarModelRequest();
    CarBrand brand = CarBrandFixture.createCarBrandWithDifferentName();
    CarType type = CarTypeFixture.createCarTypeWithDifferentName();
    carModelService.update(model, brand, type, request);
    verify(carModelRepository).save(carModelArgumentCaptor.capture());
    CarModel actualCarModel = carModelArgumentCaptor.getValue();
    assertThat(actualCarModel.getName()).isEqualTo((request.getName()));
    assertThat(actualCarModel.getCarBrand().getId()).isEqualTo(request.getCarBrandId());
    assertThat(actualCarModel.getCarType().getId()).isEqualTo(request.getCarTypeId());
  }

  @Test
  void delete() {
    CarModel model = CarModelFixture.createCarModel();
    carModelService.delete(model);
    verify(carModelRepository).save(carModelArgumentCaptor.capture());
    CarModel actualCarModel = carModelArgumentCaptor.getValue();
    assertThat(actualCarModel).isEqualTo(model);
    assertThat(actualCarModel.getActive()).isFalse();
  }
}
