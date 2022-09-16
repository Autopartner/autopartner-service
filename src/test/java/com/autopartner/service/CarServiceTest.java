package com.autopartner.service;

import com.autopartner.api.dto.request.CarRequest;
import com.autopartner.api.dto.request.CarRequestFixture;
import com.autopartner.domain.*;
import com.autopartner.repository.CarRepository;
import com.autopartner.service.impl.CarServiceImpl;
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
import java.util.Objects;
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

  @Test
  public void findAll() {
    List<Car> cars = List.of(CarFixture.createCar(), CarFixture.createCar());
    when(carRepository.findAllByActiveTrue()).thenReturn(cars);

    List<Car> actualCars = carService.findAll();

    assertThat(actualCars).isEqualTo(cars);
  }

  @Test
  public void create() {
    CarRequest carRequest = CarRequestFixture.createCarRequest();
    Client client = ClientFixture.createPersonClient();
    CarModel carModel = CarModelFixture.createCarModel();

    carService.create(carRequest, client, carModel, 2L);

    verify(carRepository).save(carArgumentCaptor.capture());
    Car actualCar = carArgumentCaptor.getValue();
    assertThatCarMappedCorrectly(actualCar, carRequest);
  }

  @Test
  void findById() {
    Car car = CarFixture.createCar();
    when(carRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.ofNullable(car));

    carService.findById(Objects.requireNonNull(car).getId());

    verify(carRepository).findByIdAndActiveTrue(longArgumentCaptor.capture());
    Long id = longArgumentCaptor.getValue();
    assertThat(id).isEqualTo(car.getId());
  }

  @Test
  void update() {
    CarRequest carRequest = CarRequestFixture.createCarRequest();
    Client client = ClientFixture.createPersonClient();
    CarModel carModel = CarModelFixture.createCarModel();
    Car car = CarFixture.createCar();

    carService.update(car, client, carModel, carRequest);

    verify(carRepository).save(carArgumentCaptor.capture());
    Car actualCar = carArgumentCaptor.getValue();
    assertThatCarMappedCorrectly(actualCar, carRequest);
  }

  @Test
  void delete() {
    Car car = CarFixture.createCar();

    carService.delete(car);

    verify(carRepository).save(carArgumentCaptor.capture());
    Car actualCar = carArgumentCaptor.getValue();
    assertThat(actualCar).isEqualTo(car);
    assertThat(actualCar.getActive()).isFalse();
  }

  private void assertThatCarMappedCorrectly(Car actualCar, CarRequest carRequest) {
    assertThat(actualCar.getPlateNumber()).isEqualTo(carRequest.getPlateNumber());
    assertThat(actualCar.getManufactureYear()).isEqualTo(carRequest.getManufactureYear());
    assertThat(actualCar.getVinCode()).isEqualTo(carRequest.getVinCode());
    assertThat(actualCar.getNote()).isEqualTo(carRequest.getNote());
  }
}
