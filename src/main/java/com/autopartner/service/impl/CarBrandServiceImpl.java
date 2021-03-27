package com.autopartner.service.impl;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.domain.CarBrand;
import com.autopartner.repository.CarBrandRepository;
import com.autopartner.service.CarBrandService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CarBrandServiceImpl implements CarBrandService {

  CarBrandRepository carBrandRepository;

  @Override
  public Iterable<CarBrand> getByActiveTrue() {
    return carBrandRepository.findByActiveTrue();
  }

  @Override
  public CarBrand getCarBrandById(Long id) {
    return carBrandRepository.findById(id).get();
  }

  @Override
  public CarBrand saveCarBrand(CarBrand carBrand) {
    return carBrandRepository.save(carBrand);
  }
}
