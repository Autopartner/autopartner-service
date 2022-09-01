package com.autopartner.api.controller;

import com.autopartner.api.dto.request.CarModelRequest;
import com.autopartner.api.dto.response.CarModelResponse;
import com.autopartner.domain.CarBrand;
import com.autopartner.domain.CarModel;
import com.autopartner.domain.CarType;
import com.autopartner.domain.User;
import com.autopartner.exception.AlreadyExistsException;
import com.autopartner.exception.NotFoundException;
import com.autopartner.service.CarBrandService;
import com.autopartner.service.CarModelService;
import com.autopartner.service.CarTypeService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@RequestMapping("/api/v1/cars/models")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CarModelController {

  CarModelService carModelService;
  CarBrandService carBrandService;
  CarTypeService carTypeService;

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @GetMapping
  public List<CarModelResponse> getAll() {
    return carModelService.findAll().stream()
        .map(CarModelResponse::fromEntity)
        .collect(Collectors.toList());
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @GetMapping(value = "/{id}")
  public CarModelResponse get(@PathVariable Long id) {
    return carModelService.findById(id)
        .map(CarModelResponse::fromEntity)
        .orElseThrow(() -> new NotFoundException("CarModel", id));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @PostMapping
  public CarModelResponse create(@Valid @RequestBody CarModelRequest request,
                                 @AuthenticationPrincipal User user) {
    log.info("Received car model registration request {}", request);
    String name = request.getName();
    if (carModelService.existsByName(name)) {
      log.error("Car model already exist with name: {}", name);
      throw new AlreadyExistsException("CarModel", name);
    }
    CarBrand brand = carBrandService.findById(request.getCarBrandId())
        .orElseThrow(() -> new NotFoundException("CarBrand", request.getCarBrandId()));
    CarType type = carTypeService.findById(request.getCarTypeId())
        .orElseThrow(() -> new NotFoundException("CarType", request.getCarTypeId()));
    CarModel model = carModelService.create(request, brand, type, user.getCompanyId());
    log.info("Created new car model {}", request.getName());
    return CarModelResponse.fromEntity(model);
  }

  @PutMapping("/{id}")
  @Secured("ROLE_USER")
  public CarModelResponse update(@PathVariable Long id, @RequestBody @Valid CarModelRequest request) {
    CarModel model = carModelService.findById(id)
        .orElseThrow(() -> new NotFoundException("CarModel", id));
    CarBrand brand = carBrandService.findById(request.getCarBrandId())
        .orElseThrow(() -> new NotFoundException("CarBrand", request.getCarBrandId()));
    CarType type = carTypeService.findById(request.getCarTypeId())
        .orElseThrow(() -> new NotFoundException("CarType", request.getCarTypeId()));
    return CarModelResponse.fromEntity(carModelService.update(model, brand, type, request));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable Long id) {
    CarModel carModel = carModelService.findById(id)
        .orElseThrow(() -> new NotFoundException("CarModel", id));
    carModelService.delete(carModel);
  }
}
