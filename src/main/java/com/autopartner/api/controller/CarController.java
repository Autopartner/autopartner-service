package com.autopartner.api.controller;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.api.dto.request.CarRequest;
import com.autopartner.api.dto.response.CarResponse;
import com.autopartner.domain.*;
import com.autopartner.exception.NotFoundException;
import com.autopartner.service.CarModelService;
import com.autopartner.service.CarService;

import javax.validation.Valid;

import com.autopartner.service.ClientService;
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
  ClientService clientService;
  CarModelService carModelService;

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
    Client client = clientService.findById(request.getClientId())
            .orElseThrow(() -> new NotFoundException("Client", request.getClientId()));
    CarModel carModel = carModelService.findById(request.getCarModelId())
            .orElseThrow(() -> new NotFoundException("Car model", request.getCarModelId()));
    Car newCar = carService.create(request, client, carModel, user.getCompanyId());
    log.info("Created new car {}", newCar.getPlateNumber());
    return CarResponse.fromEntity(newCar);
  }

  @PutMapping("/{id}")
  @Secured("ROLE_USER")
  public CarResponse update(@PathVariable Long id,
                            @RequestBody @Valid CarRequest request) {
    Car car = carService.findById(id)
            .orElseThrow(() -> new NotFoundException("Car", id));
    Client client = clientService.findById(request.getClientId())
            .orElseThrow(() -> new NotFoundException("Client", request.getClientId()));
    CarModel carModel = carModelService.findById(request.getCarModelId())
            .orElseThrow(() -> new NotFoundException("Car model", request.getCarModelId()));
    return CarResponse.fromEntity(carService.update(car, client, carModel, request));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable Long id) {
    Car car = carService.findById(id)
            .orElseThrow(() -> new NotFoundException("Car", id));
    carService.delete(car);
  }
}
