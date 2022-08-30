package com.autopartner.service;

import com.autopartner.api.dto.request.CarRequest;
import com.autopartner.domain.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {

  List<Car> findAll();

  Optional<Car> findById(Long id);

  void delete(Car car);

  Car create(CarRequest request, Long companyId);

  Car update(Car car, CarRequest request);

}
