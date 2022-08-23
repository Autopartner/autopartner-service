package com.autopartner.service;

import com.autopartner.api.dto.request.CarTypeRequestFixture;
import com.autopartner.api.dto.request.CarTypeRequest;
import com.autopartner.domain.CarType;
import com.autopartner.domain.CarTypeFixture;
import com.autopartner.repository.CarTypeRepository;
import com.autopartner.service.impl.CarTypeServiceImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
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
public class CarTypeTest {
    @Mock
    CarTypeRepository carTypeRepository;

    @InjectMocks
    CarTypeServiceImpl carTypeService;

    @Captor
    ArgumentCaptor<CarType> carTypeArgumentCaptor;

    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;

    List<CarType> carTypes;

    CarType carType;
    CarTypeRequest request;

    Long id;

    @BeforeEach
    public void init() {
        carType = CarTypeFixture.createCarType();
        carTypes = List.of(carType, new CarType());
        request = CarTypeRequestFixture.createCarTypeRequest();
        id = 3L;
    }

    @Test
    void findAll() {
        when(carTypeRepository.findByActiveTrue()).thenReturn(carTypes);
        List<CarType> actualCarTypes = carTypeService.findAll();
        assertThat(carTypes).isEqualTo(actualCarTypes);
    }

    @Test
    void create() {
        carTypeService.create(request, id);
        verify(carTypeRepository).save(carTypeArgumentCaptor.capture());
        CarType actualCarType = carTypeArgumentCaptor.getValue();
        assertThat(actualCarType.getName()).isEqualTo((request.getName()));
    }

    @Test
    void findById() {
        when(carTypeRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.ofNullable(carType));
        carTypeService.findById(carType.getId());
        verify(carTypeRepository).findByIdAndActiveTrue(longArgumentCaptor.capture());
        id = longArgumentCaptor.getValue();
        assertThat(id).isEqualTo(carType.getId());
    }

    @Test
    void update() {
        carTypeService.update(carType, request);
        verify(carTypeRepository).save(carTypeArgumentCaptor.capture());
        CarType actualCarType = carTypeArgumentCaptor.getValue();
        assertThat(actualCarType.getName()).isEqualTo((request.getName()));
    }

    @Test
    void delete() {
        carTypeService.delete(carType);
        verify(carTypeRepository).save(carTypeArgumentCaptor.capture());
        CarType actualCarType = carTypeArgumentCaptor.getValue();
        assertThat(actualCarType).isEqualTo(carType);
        assertThat(actualCarType.getActive()).isFalse();
    }
}
