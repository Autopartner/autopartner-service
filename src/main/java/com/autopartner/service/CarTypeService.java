package com.autopartner.service;

import com.autopartner.domain.CarType;

public interface CarTypeService {

  Iterable<CarType> getByActiveTrue();

  CarType getCarTypeById(Long id);

  CarType saveCarType(CarType carType);

}
