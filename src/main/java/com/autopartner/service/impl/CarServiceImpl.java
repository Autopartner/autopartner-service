package com.autopartner.service.impl;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.api.dto.request.CarRequest;
import com.autopartner.domain.Car;
import com.autopartner.domain.CarModel;
import com.autopartner.domain.Client;
import com.autopartner.repository.CarRepository;
import com.autopartner.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CarServiceImpl implements CarService {

  CarRepository carRepository;

  @Override
  public List<Car> findAll() {
    return carRepository.findAllByActiveTrue();
  }

  @Override
  public Optional<Car> findById(Long id) {
    return carRepository.findByIdAndActiveTrue(id);
  }

  private Car save(Car car) {
    return carRepository.save(car);
  }

  @Override
  public Car update(Car car, Client client, CarModel carModel, CarRequest request) {
    car.update(request, client, carModel);
    return save(car);
  }

  @Override
  public void delete(Car car) {
    car.delete();
    save(car);
  }

  @Override
  public Car create(CarRequest request, Client client, CarModel carModel, Long companyId) {
    return save(Car.create(request, client, carModel, companyId));
  }
}
