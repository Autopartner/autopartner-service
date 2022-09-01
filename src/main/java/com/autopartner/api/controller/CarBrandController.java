package com.autopartner.api.controller;

import com.autopartner.api.dto.request.CarBrandRequest;
import com.autopartner.api.dto.response.CarBrandResponse;
import com.autopartner.domain.CarBrand;
import com.autopartner.domain.User;
import com.autopartner.exception.AlreadyExistsException;
import com.autopartner.exception.NotFoundException;
import com.autopartner.service.CarBrandService;
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
@RequestMapping("/api/v1/cars/brands")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CarBrandController {

  CarBrandService carBrandService;

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @GetMapping
  public List<CarBrandResponse> getAll() {
    return carBrandService.findAll().stream()
        .map(CarBrandResponse::fromEntity)
        .collect(Collectors.toList());
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @GetMapping(value = "/{id}")
  public CarBrandResponse get(@PathVariable Long id) {
    return carBrandService.findById(id)
        .map(CarBrandResponse::fromEntity)
        .orElseThrow(() -> new NotFoundException("CarBrand", id));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @PostMapping
  public CarBrandResponse create(@Valid @RequestBody CarBrandRequest request,
                                 @AuthenticationPrincipal User user) {
    log.info("Received car brand registration request {}", request);
    String name = request.getName();
    if (carBrandService.existsByName(name)) {
      log.error("Car Brand already exist with name: {}", name);
      throw new AlreadyExistsException("CarBrand", name);
    }
    CarBrand carBrand = carBrandService.create(request, user.getCompanyId());
    log.info("Created new client {}", request.getName());
    return CarBrandResponse.fromEntity(carBrand);
  }

  @PutMapping("/{id}")
  @Secured("ROLE_USER")
  public CarBrandResponse update(@PathVariable Long id, @RequestBody @Valid CarBrandRequest request) {
    CarBrand carBrand = carBrandService.findById(id)
        .orElseThrow(() -> new NotFoundException("CarBrand", id));
    return CarBrandResponse.fromEntity(carBrandService.update(carBrand, request));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable Long id) {
    CarBrand carBrand = carBrandService.findById(id)
        .orElseThrow(() -> new NotFoundException("CarBrand", id));
    carBrandService.delete(carBrand);
  }
}
