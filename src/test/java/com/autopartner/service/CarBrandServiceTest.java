package com.autopartner.service;

import com.autopartner.api.dto.request.CarBrandRequest;
import com.autopartner.api.dto.request.CarBrandRequestFixture;
import com.autopartner.domain.CarBrand;
import com.autopartner.domain.CarBrandFixture;
import com.autopartner.repository.CarBrandRepository;
import com.autopartner.service.impl.CarBrandServiceImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PACKAGE)
public class CarBrandServiceTest {

  @Mock
  CarBrandRepository carBrandRepository;
  @InjectMocks
  CarBrandServiceImpl carBrandService;
  @Captor
  ArgumentCaptor<CarBrand> carBrandArgumentCaptor;
  @Captor
  ArgumentCaptor<Long> carBrandIdArgumentCaptor;
  @Captor
  ArgumentCaptor<Long> companyIdArgumentCaptor;

  @Test
  void findAll() {
    Long companyId = 1L;
    List<CarBrand> brands = List.of(CarBrandFixture.createCarBrand());
    when(carBrandRepository.findAll(companyId)).thenReturn(brands);
    List<CarBrand> actualCarBrands = carBrandService.findAll(companyId);
    assertThat(brands).isEqualTo(actualCarBrands);
  }

  @Test
  void create() {
    CarBrandRequest request = CarBrandRequestFixture.createCarBrandRequest();
    long id = 1L;
    carBrandService.create(request, id);
    verify(carBrandRepository).save(carBrandArgumentCaptor.capture());
    CarBrand actualCarBrands = carBrandArgumentCaptor.getValue();
    assertThat(actualCarBrands.getName()).isEqualTo((request.getName()));
  }

  @Test
  void findById() {
    Long companyId = 1L;
    CarBrand brand = CarBrandFixture.createCarBrandWithDifferentName();
    when(carBrandRepository.findById(anyLong(), anyLong())).thenReturn(Optional.ofNullable(brand));
    carBrandService.findById(brand.getId(), companyId);
    verify(carBrandRepository).findById(carBrandIdArgumentCaptor.capture(), companyIdArgumentCaptor.capture());
    long id = carBrandIdArgumentCaptor.getValue();
    long currentCompanyId = companyIdArgumentCaptor.getValue();
    assertThat(id).isEqualTo(brand.getId());
    assertThat(currentCompanyId).isEqualTo(companyId);
  }

  @Test
  void update() {
    CarBrand brand = CarBrandFixture.createCarBrandWithDifferentName();
    CarBrandRequest request = CarBrandRequestFixture.createCarBrandRequest();
    carBrandService.update(brand, request);
    verify(carBrandRepository).save(carBrandArgumentCaptor.capture());
    CarBrand actualCarBrand = carBrandArgumentCaptor.getValue();
    assertThat(actualCarBrand.getName()).isEqualTo((request.getName()));
  }

  @Test
  void delete() {
    CarBrand brand = CarBrandFixture.createCarBrandWithDifferentName();
    carBrandService.delete(brand);
    verify(carBrandRepository).save(carBrandArgumentCaptor.capture());
    CarBrand actualCarBrand = carBrandArgumentCaptor.getValue();
    assertThat(actualCarBrand).isEqualTo(brand);
    assertThat(actualCarBrand.getActive()).isFalse();
  }
}
