package com.autopartner.api.controller;

import com.autopartner.api.dto.request.CarTypeRequest;
import com.autopartner.api.dto.response.CarTypeResponse;
import com.autopartner.domain.CarType;
import com.autopartner.domain.User;
import com.autopartner.exception.AlreadyExistsException;
import com.autopartner.exception.NotFoundException;
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
@RequestMapping("/api/v1/cars/types")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CarTypeController {

  CarTypeService carTypeService;

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @GetMapping
  public List<CarTypeResponse> getAll() {
    return carTypeService.findAll().stream()
        .map(CarTypeResponse::fromEntity)
        .collect(Collectors.toList());
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @GetMapping(value = "/{id}")
  public CarTypeResponse get(@PathVariable Long id) {
    return carTypeService.findById(id)
        .map(CarTypeResponse::fromEntity)
        .orElseThrow(() -> new NotFoundException("CarType", id));
  }

  @PostMapping
  public CarTypeResponse create(@Valid @RequestBody CarTypeRequest request,
                                @AuthenticationPrincipal User user) {
    log.info("Received car type registration request {}", request);
    String name = request.getName();
    if (carTypeService.existsByName(name)) {
      throw new AlreadyExistsException("CarType", name);
    }
    CarType carType = carTypeService.create(request, user.getCompanyId());
    log.info("Created new car type {}", request.getName());
    return CarTypeResponse.fromEntity(carType);
  }

  @PutMapping("/{id}")
  @Secured("ROLE_USER")
  public CarTypeResponse update(@PathVariable Long id, @RequestBody @Valid CarTypeRequest carTypeRequest) {
    CarType carType = carTypeService.findById(id)
        .orElseThrow(() -> new NotFoundException("CarType", id));
    return CarTypeResponse.fromEntity(carTypeService.update(carType, carTypeRequest));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable Long id) {
    CarType carType = carTypeService.findById(id)
        .orElseThrow(() -> new NotFoundException("CarType", id));
    carTypeService.delete(carType);
  }
}
