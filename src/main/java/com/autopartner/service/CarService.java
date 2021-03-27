package com.autopartner.service;

import com.autopartner.domain.Car;

public interface CarService {

  Iterable<Car> getByActiveTrue();

  Car getCarById(Long id);

  Car saveCar(Car car);

}
