package com.autopartner.service.impl;

import com.autopartner.api.dto.request.CarTypeRequest;
import com.autopartner.domain.CarType;
import com.autopartner.repository.CarTypeRepository;
import com.autopartner.service.CarTypeService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CarTypeServiceImpl implements CarTypeService {

  CarTypeRepository carTypeRepository;

  @Override
  public List<CarType> findAll(Long companyId) {
    return carTypeRepository.findAll(companyId);
  }

  @Override
  public Optional<CarType> findById(Long id, Long companyId) {
    return carTypeRepository.findById(id, companyId);
  }

  private CarType save(CarType carType) {
    return carTypeRepository.save(carType);
  }

  @Override
  public CarType update(CarType carType, CarTypeRequest request) {
    carType.update(request);
    return save(carType);
  }

  @Override
  public void delete(CarType carType) {
    carType.delete();
    save(carType);
  }

  @Override
  public CarType create(CarTypeRequest request, Long companyId) {
    return save(CarType.create(request, companyId));
  }

  @Override
  public Optional<Long> findIdByName(String name, Long companyId) {
    return carTypeRepository.findIdByName(name, companyId);
  }

}
