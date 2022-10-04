package com.autopartner.service;

import com.autopartner.api.dto.request.CarTypeRequest;
import com.autopartner.domain.CarType;

import java.util.List;
import java.util.Optional;

public interface CarTypeService {

  List<CarType> findAll(Long companyId);

  Optional<CarType> findById(Long id, Long companyId);

  void delete(CarType carType);

  CarType create(CarTypeRequest request, Long companyId);

  CarType update(CarType carType, CarTypeRequest request);

  Optional<Long> findIdByName(String name, Long companyId);

}
