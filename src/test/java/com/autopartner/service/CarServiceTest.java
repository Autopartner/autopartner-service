package com.autopartner.service;

import com.autopartner.api.dto.request.CarRequest;
import com.autopartner.api.dto.request.CarRequestFixture;
import com.autopartner.domain.Car;
import com.autopartner.domain.CarFixture;
import com.autopartner.repository.CarRepository;
import com.autopartner.service.impl.CarServiceImpl;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PACKAGE)
public class CarServiceTest {

  @Mock
  CarRepository carRepository;

  @InjectMocks
  CarServiceImpl carService;

  @Captor
  ArgumentCaptor<Car> carArgumentCaptor;

  @Captor
  ArgumentCaptor<Long> longArgumentCaptor;

  List<Car> cars;

  Car car;

  CarRequest request;

  Long id;

  @BeforeEach
  public void init() {
    car = CarFixture.createCar();
    cars = List.of(car, new Car());
    request = CarRequestFixture.createCarRequest();
    id = 2L;
  }

  @Test
  public void findAll() {
    when(carRepository.findAllByActiveTrue()).thenReturn(cars);
    List<Car> actualCars = carService.findAll();
    assertThat(cars).isEqualTo(actualCars);
  }

  @Test
  public void create() {
    carService.create(request, id);
    verify(carRepository).save(carArgumentCaptor.capture());
    Car actualCar = carArgumentCaptor.getValue();
    assertThatCarMappedCorrectly(actualCar);
  }

  @Test
  void findById() {
    when(carRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.ofNullable(car));
    carService.findById(car.getId());
    verify(carRepository).findByIdAndActiveTrue(longArgumentCaptor.capture());
    id = longArgumentCaptor.getValue();
    assertThat(id).isEqualTo(car.getId());
  }

  @Test
  void update() {
    carService.update(car, request);
    verify(carRepository).save(carArgumentCaptor.capture());
    Car actualCar = carArgumentCaptor.getValue();
    assertThatCarMappedCorrectly(actualCar);
  }

  @Test
  void delete() {
    carService.delete(car);
    verify(carRepository).save(carArgumentCaptor.capture());
    Car actualCar = carArgumentCaptor.getValue();
    assertThat(actualCar).isEqualTo(car);
    assertThat(actualCar.getActive()).isFalse();
  }

  private void assertThatCarMappedCorrectly(Car actualCar) {
    assertThat(actualCar.getPlateNumber()).isEqualTo(request.getPlateNumber());
    assertThat(actualCar.getManufactureYear()).isEqualTo(request.getManufactureYear());
    assertThat(actualCar.getVinCode()).isEqualTo(request.getVinCode());
    assertThat(actualCar.getNote()).isEqualTo(request.getNote());
  }
}
