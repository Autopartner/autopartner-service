package com.autopartner.api.controller;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.api.dto.request.CarRequest;
import com.autopartner.api.dto.response.CarResponse;
import com.autopartner.domain.Car;
import com.autopartner.domain.User;
import com.autopartner.exception.NotFoundException;
import com.autopartner.service.CarService;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CarController {

  CarService carService;

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @GetMapping
  public List<CarResponse> getAll() {
    return carService.findAll().stream()
            .map(CarResponse::fromEntity)
            .collect(Collectors.toList());
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @GetMapping(value = "/{id}")
  public CarResponse get(@PathVariable Long id) {
    return carService.findById(id)
            .map(CarResponse::fromEntity)
            .orElseThrow(() -> new NotFoundException("Car", id));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @PostMapping
  public CarResponse create(@Valid @RequestBody CarRequest request,
                            @AuthenticationPrincipal User user) {
    log.info("Received car registration request {}", request);
    Car newCar = carService.create(request, user.getCompanyId());
    log.info("Created new car {}", newCar.getPlateNumber());
    return CarResponse.fromEntity(newCar);
  }

  @PutMapping("/{id}")
  @Secured("ROLE_USER")
  public CarResponse update(@PathVariable Long id,
                            @RequestBody @Valid CarRequest request) {
    Car car = carService.findById(id)
            .orElseThrow(() -> new NotFoundException("Car", id));
    return CarResponse.fromEntity(carService.update(car, request));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable Long id) {
    Car car = carService.findById(id)
            .orElseThrow(() -> new NotFoundException("Car", id));
    carService.delete(car);
  }
}
