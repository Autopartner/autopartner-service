package com.autopartner.service.impl;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.domain.CarType;
import com.autopartner.repository.CarTypeRepository;
import com.autopartner.service.CarTypeService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Service("carTypeService")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CarTypeServiceImpl implements CarTypeService {

  CarTypeRepository carTypeRepository;

  @Override
  public Iterable<CarType> getByActiveTrue() {
    return carTypeRepository.findByActiveTrue();
  }

  @Override
  public CarType getCarTypeById(Long id) {
    return carTypeRepository.findById(id).get();
  }

  @Override
  public CarType saveCarType(CarType carType) {
    return carTypeRepository.save(carType);
  }
}
