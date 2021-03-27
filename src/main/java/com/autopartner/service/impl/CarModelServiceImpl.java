package com.autopartner.service.impl;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.domain.CarModel;
import com.autopartner.repository.CarModelRepository;
import com.autopartner.service.CarModelService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CarModelServiceImpl implements CarModelService {

  CarModelRepository carModelRepository;

  @Override
  public Iterable<CarModel> getByActiveTrue() {
    return carModelRepository.findByActiveTrue();
  }

  @Override
  public CarModel getCarModelById(Long id) {
    return carModelRepository.findById(id).get();
  }

  @Override
  public CarModel saveCarModel(CarModel carModel) {
    return carModelRepository.save(carModel);
  }
}
