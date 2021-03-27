package com.autopartner.service.impl;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.domain.Car;
import com.autopartner.repository.CarRepository;
import com.autopartner.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Service("carService")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CarServiceImpl implements CarService {

  CarRepository carRepository;

  @Override
  public Iterable<Car> getByActiveTrue() {
    return carRepository.findByActiveTrue();
  }

  @Override
  public Car getCarById(Long id) {
    return carRepository.findById(id).get();
  }

  @Override
  public Car saveCar(Car car) {
    return carRepository.save(car);
  }
}
