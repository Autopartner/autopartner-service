package com.autopartner.service;

import com.autopartner.api.dto.request.CarBrandRequest;
import com.autopartner.domain.CarBrand;

import java.util.List;
import java.util.Optional;

public interface CarBrandService {

  List<CarBrand> findAll(Long companyId);

  Optional<CarBrand> findById(Long id, Long companyId);

  void delete(CarBrand carBrand);

  CarBrand create(CarBrandRequest request, Long companyId);

  CarBrand update(CarBrand carType, CarBrandRequest request);

  Optional<Long> findIdByName(String name, Long companyId);

}
