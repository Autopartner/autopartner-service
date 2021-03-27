package com.autopartner.service;

import com.autopartner.domain.CarBrand;

public interface CarBrandService {

  Iterable<CarBrand> getByActiveTrue();

  CarBrand getCarBrandById(Long id);

  CarBrand saveCarBrand(CarBrand carBrand);

}
