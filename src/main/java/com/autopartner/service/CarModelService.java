package com.autopartner.service;

import com.autopartner.domain.CarModel;

public interface CarModelService {

  Iterable<CarModel> getByActiveTrue();

  CarModel getCarModelById(Long id);

  CarModel saveCarModel(CarModel carModel);

}
