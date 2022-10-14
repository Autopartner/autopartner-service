package com.autopartner.api.controller;

import com.autopartner.api.dto.request.CarRequest;
import com.autopartner.api.dto.response.CarResponse;
import com.autopartner.domain.Car;
import com.autopartner.domain.CarModel;
import com.autopartner.domain.Client;
import com.autopartner.domain.User;
import com.autopartner.exception.AlreadyExistsException;
import com.autopartner.exception.NotFoundException;
import com.autopartner.service.CarModelService;
import com.autopartner.service.CarService;
import com.autopartner.service.ClientService;
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
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CarController {

  CarService carService;
  ClientService clientService;
  CarModelService carModelService;

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @GetMapping
  public List<CarResponse> getAll(@AuthenticationPrincipal User user) {
    return carService.findAll(user.getCompanyId()).stream()
        .map(CarResponse::fromEntity)
        .collect(Collectors.toList());
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @GetMapping(value = "/{id}")
  public CarResponse get(@PathVariable Long id, @AuthenticationPrincipal User user) {
    return carService.findById(id, user.getCompanyId())
        .map(CarResponse::fromEntity)
        .orElseThrow(() -> new NotFoundException("Car", id));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @PostMapping
  public CarResponse create(@Valid @RequestBody CarRequest request, @AuthenticationPrincipal User user) {
    log.info("Received car registration request {}", request);
    Long companyId = user.getCompanyId();
    String vinCode = request.getVinCode();
    if (carService.findIdByVinCode(vinCode, companyId).isPresent()) {
      throw new AlreadyExistsException("Car", vinCode);
    }
    Client client = clientService.findById(request.getClientId(), companyId)
        .orElseThrow(() -> new NotFoundException("Client", request.getClientId()));
    CarModel carModel = carModelService.findById(request.getCarModelId(), companyId)
        .orElseThrow(() -> new NotFoundException("Car model", request.getCarModelId()));
    Car newCar = carService.create(request, client, carModel, companyId);
    log.info("Created new car {}", newCar.getPlateNumber());
    return CarResponse.fromEntity(newCar);
  }

  @PutMapping("/{id}")
  @Secured("ROLE_USER")
  public CarResponse update(@PathVariable Long id,
                            @RequestBody @Valid CarRequest request, @AuthenticationPrincipal User user) {
    Long companyId = user.getCompanyId();
    String vinCode = request.getVinCode();
    Car car = carService.findById(id, companyId)
        .orElseThrow(() -> new NotFoundException("Car", id));
    Client client = clientService.findById(request.getClientId(), companyId)
        .orElseThrow(() -> new NotFoundException("Client", request.getClientId()));
    CarModel carModel = carModelService.findById(request.getCarModelId(), companyId)
        .orElseThrow(() -> new NotFoundException("Car model", request.getCarModelId()));
    Optional<Long> foundId = carService.findIdByVinCode(vinCode, companyId);
    if (foundId.isPresent() && !foundId.get().equals(id)) {
      throw new AlreadyExistsException("Car", vinCode);
    }
    return CarResponse.fromEntity(carService.update(car, client, carModel, request));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
    Car car = carService.findById(id, user.getCompanyId())
        .orElseThrow(() -> new NotFoundException("Car", id));
    carService.delete(car);
  }
}
