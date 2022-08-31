package com.autopartner.service;

import com.autopartner.api.dto.request.CarRequest;
import com.autopartner.domain.Car;
import com.autopartner.domain.CarModel;
import com.autopartner.domain.Client;

import java.util.List;
import java.util.Optional;

public interface CarService {

  List<Car> findAll();

  Optional<Car> findById(Long id);

  void delete(Car car);

  Car create(CarRequest request, Client client, CarModel carModel, Long companyId);

  Car update(Car car, Client client, CarModel carModel, CarRequest request);

}
