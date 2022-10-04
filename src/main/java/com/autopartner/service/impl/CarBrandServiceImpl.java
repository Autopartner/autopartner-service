package com.autopartner.service.impl;

import com.autopartner.api.dto.request.CarBrandRequest;
import com.autopartner.domain.CarBrand;
import com.autopartner.repository.CarBrandRepository;
import com.autopartner.service.CarBrandService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CarBrandServiceImpl implements CarBrandService {

  CarBrandRepository carBrandRepository;

  @Override
  public List<CarBrand> findAll(Long companyId) {
    return carBrandRepository.findAll(companyId);
  }

  @Override
  public Optional<CarBrand> findById(Long id, Long companyId) {
    return carBrandRepository.findById(id, companyId);
  }

  private CarBrand save(CarBrand carBrand) {
    return carBrandRepository.save(carBrand);
  }

  @Override
  public CarBrand update(CarBrand carBrand, CarBrandRequest request) {
    carBrand.update(request);
    return save(carBrand);
  }

  @Override
  public void delete(CarBrand carBrand) {
    carBrand.delete();
    save(carBrand);
  }

  @Override
  public CarBrand create(CarBrandRequest request, Long companyId) {
    return save(CarBrand.create(request, companyId));
  }

  @Override
  public Optional<Long> findIdByName(String name, Long companyId) {
    return carBrandRepository.findIdByNameAndActiveTrue(name, companyId);
  }
}
