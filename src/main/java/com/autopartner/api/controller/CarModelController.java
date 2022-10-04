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
import java.util.Optional;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@RequestMapping("/api/v1/car-models")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CarModelController {

  CarModelService carModelService;
  CarBrandService carBrandService;
  CarTypeService carTypeService;

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @GetMapping
  public List<CarModelResponse> getAll(@AuthenticationPrincipal User user) {
    return carModelService.findAll(user.getCompanyId()).stream()
        .map(CarModelResponse::fromEntity)
        .collect(Collectors.toList());
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @GetMapping(value = "/{id}")
  public CarModelResponse get(@PathVariable Long id, @AuthenticationPrincipal User user) {
    return carModelService.findById(id, user.getCompanyId())
        .map(CarModelResponse::fromEntity)
        .orElseThrow(() -> new NotFoundException("CarModel", id));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @PostMapping
  public CarModelResponse create(@Valid @RequestBody CarModelRequest request,
                                 @AuthenticationPrincipal User user) {
    log.info("Received car model registration request {}", request);
    String name = request.getName();
    if (carModelService.findIdByName(name, user.getCompanyId()).isPresent()) {
      throw new AlreadyExistsException("CarModel", name);
    }
    CarBrand brand = carBrandService.findById(request.getCarBrandId(), user.getCompanyId())
        .orElseThrow(() -> new NotFoundException("CarBrand", request.getCarBrandId()));
    CarType type = carTypeService.findById(request.getCarTypeId(), user.getCompanyId())
        .orElseThrow(() -> new NotFoundException("CarType", request.getCarTypeId()));
    CarModel model = carModelService.create(request, brand, type, user.getCompanyId());
    log.info("Created new car model {}", request.getName());
    return CarModelResponse.fromEntity(model);
  }

  @PutMapping("/{id}")
  @Secured("ROLE_USER")
  public CarModelResponse update(@PathVariable Long id, @RequestBody @Valid CarModelRequest request,
                                 @AuthenticationPrincipal User user) {
    CarModel model = carModelService.findById(id, user.getCompanyId())
        .orElseThrow(() -> new NotFoundException("CarModel", id));
    CarBrand brand = carBrandService.findById(request.getCarBrandId(), user.getCompanyId())
        .orElseThrow(() -> new NotFoundException("CarBrand", request.getCarBrandId()));
    CarType type = carTypeService.findById(request.getCarTypeId(), user.getCompanyId())
        .orElseThrow(() -> new NotFoundException("CarType", request.getCarTypeId()));
    Optional<Long> foundId = carModelService.findIdByName(request.getName(), user.getCompanyId());
    if (foundId.isPresent() && !foundId.get().equals(id)) {
      throw new AlreadyExistsException("CarModel", request.getName());
    }
    return CarModelResponse.fromEntity(carModelService.update(model, brand, type, request));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
    CarModel carModel = carModelService.findById(id, user.getCompanyId())
        .orElseThrow(() -> new NotFoundException("CarModel", id));
    carModelService.delete(carModel);
  }
}
