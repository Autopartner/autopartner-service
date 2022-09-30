package com.autopartner.service;

import com.autopartner.api.dto.request.CarTypeRequest;
import com.autopartner.api.dto.request.CarTypeRequestFixture;
import com.autopartner.domain.CarType;
import com.autopartner.domain.CarTypeFixture;
import com.autopartner.repository.CarTypeRepository;
import com.autopartner.service.impl.CarTypeServiceImpl;
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
public class CarTypeServiceTest {

  @Mock
  CarTypeRepository carTypeRepository;
  @InjectMocks
  CarTypeServiceImpl carTypeService;
  @Captor
  ArgumentCaptor<CarType> carTypeArgumentCaptor;
  @Captor
  ArgumentCaptor<Long> longArgumentCaptor;

  @Test
  void findAll() {
    List<CarType> types = List.of(CarTypeFixture.createCarType());
    when(carTypeRepository.findByActiveTrue()).thenReturn(types);
    List<CarType> actualCarTypes = carTypeService.findAll();
    assertThat(types).isEqualTo(actualCarTypes);
  }

  @Test
  void create() {
    CarTypeRequest request = CarTypeRequestFixture.createCarTypeRequest();
    long id = 1L;
    carTypeService.create(request, id);
    verify(carTypeRepository).save(carTypeArgumentCaptor.capture());
    CarType actualCarType = carTypeArgumentCaptor.getValue();
    assertThat(actualCarType.getName()).isEqualTo((request.getName()));
  }

  @Test
  void findById() {
    CarType type = CarTypeFixture.createCarTypeWithDifferentName();
    when(carTypeRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.ofNullable(type));
    carTypeService.findById(type.getId());
    verify(carTypeRepository).findByIdAndActiveTrue(longArgumentCaptor.capture());
    long id = longArgumentCaptor.getValue();
    assertThat(id).isEqualTo(type.getId());
  }

  @Test
  void update() {
    CarType type = CarTypeFixture.createCarTypeWithDifferentName();
    CarTypeRequest request = CarTypeRequestFixture.createCarTypeRequest();
    carTypeService.update(type, request);
    verify(carTypeRepository).save(carTypeArgumentCaptor.capture());
    CarType actualCarType = carTypeArgumentCaptor.getValue();
    assertThat(actualCarType.getName()).isEqualTo((request.getName()));
  }

  @Test
  void delete() {
    CarType type = CarTypeFixture.createCarTypeWithDifferentName();
    carTypeService.delete(type);
    verify(carTypeRepository).save(carTypeArgumentCaptor.capture());
    CarType actualCarType = carTypeArgumentCaptor.getValue();
    assertThat(actualCarType).isEqualTo(type);
    assertThat(actualCarType.getActive()).isFalse();
  }
}
