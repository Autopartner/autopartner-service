package com.autopartner.service;

import com.autopartner.api.dto.request.CarTypeRequest;
import com.autopartner.domain.CarType;

import java.util.List;
import java.util.Optional;

public interface CarTypeService {

    List<CarType> findAll();

    Optional<CarType> findById(Long id);

    void delete(CarType carType);

    CarType create(CarTypeRequest request, Long companyId);

    CarType update(CarType carType, CarTypeRequest request);

    boolean existsByName(String name);

}
