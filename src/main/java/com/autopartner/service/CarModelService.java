package com.autopartner.service;

import com.autopartner.api.dto.request.CarModelRequest;
import com.autopartner.domain.CarBrand;
import com.autopartner.domain.CarModel;
import com.autopartner.domain.CarType;

import java.util.List;
import java.util.Optional;

public interface CarModelService {

    List<CarModel> findAll();

    Optional<CarModel> findById(Long id);

    void delete(CarModel carBrand);

    CarModel create(CarModelRequest request, CarBrand brand, CarType type, Long companyId);

    CarModel update(CarModel model, CarBrand brand, CarType type, CarModelRequest request);

    boolean existsByName(String name);

}
