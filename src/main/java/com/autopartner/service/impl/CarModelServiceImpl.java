package com.autopartner.service.impl;

import com.autopartner.api.dto.request.CarModelRequest;
import com.autopartner.domain.CarBrand;
import com.autopartner.domain.CarModel;
import com.autopartner.domain.CarType;
import com.autopartner.repository.CarModelRepository;
import com.autopartner.service.CarModelService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CarModelServiceImpl implements CarModelService {

    CarModelRepository carModelRepository;


    @Override
    public List<CarModel> findAll() {
        return carModelRepository.findByActiveTrue();
    }

    @Override
    public Optional<CarModel> findById(Long id) {
        return carModelRepository.findByIdAndActiveTrue(id);
    }

    private CarModel save(CarModel carModel) {
        return carModelRepository.save(carModel);
    }

    @Override
    public CarModel update(CarModel model, CarBrand brand, CarType type, CarModelRequest request) {
        model.update(request, brand, type);
        return save(model);
    }

    @Override
    public void delete(CarModel carBrand) {
        carBrand.delete();
        save(carBrand);
    }

    @Override
    public CarModel create(CarModelRequest request, CarBrand brand, CarType type, Long companyId) {
        return save(CarModel.create(request, brand, type, companyId));
    }

    @Override
    public boolean existsByName(String name) {
        return carModelRepository.existsByNameAndActiveTrue(name);
    }
}
