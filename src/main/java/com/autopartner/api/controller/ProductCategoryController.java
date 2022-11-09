package com.autopartner.api.controller;

import com.autopartner.api.dto.request.ProductCategoryRequest;
import com.autopartner.api.dto.response.ProductCategoryResponse;
import com.autopartner.domain.ProductCategory;
import com.autopartner.domain.User;
import com.autopartner.exception.AlreadyExistsException;
import com.autopartner.exception.NotFoundException;
import com.autopartner.service.ProductCategoryService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@RequestMapping("/api/v1/product-categories")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ProductCategoryController {

  ProductCategoryService productCategoryService;

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @GetMapping
  public List<ProductCategoryResponse> getAll(@AuthenticationPrincipal User user) {
    return productCategoryService.findAll(user.getCompanyId()).stream()
        .map(ProductCategoryResponse::fromEntity)
        .collect(Collectors.toList());
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @GetMapping(value = "/{id}")
  public ProductCategoryResponse get(@PathVariable Long id, @AuthenticationPrincipal User user) {
    return productCategoryService
        .findById(id, user.getCompanyId())
        .map(ProductCategoryResponse::fromEntity)
        .orElseThrow(() -> new NotFoundException("ProductCategory", id));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @PostMapping
  public ProductCategoryResponse create(@Valid @RequestBody ProductCategoryRequest request,
      @AuthenticationPrincipal User user) {

    Long companyId = user.getCompanyId();
    log.info("CompanyId: {}, received product category registration request {}", companyId, request);

    String name = request.getName();
    if (productCategoryService.findIdByName(name, companyId).isPresent()) {
      throw new AlreadyExistsException("ProductCategory", name);
    }

    ProductCategory productCategory = productCategoryService.create(request, companyId);
    log.info("CompanyId: {}, Created new product category {}", companyId, name);
    return ProductCategoryResponse.fromEntity(productCategory);
  }

  @PutMapping("/{id}")
  @Secured("ROLE_USER")
  public ProductCategoryResponse update(@PathVariable Long id, @RequestBody @Valid ProductCategoryRequest request,
      @AuthenticationPrincipal User user) {
    Long companyId = user.getCompanyId();
    ProductCategory category = productCategoryService.findById(id, companyId)
        .orElseThrow(() -> new NotFoundException("ProductCategory", id));
    String name = request.getName();
    Optional<Long> foundId = productCategoryService.findIdByName(name, companyId);
    if (foundId.isPresent() && !foundId.get().equals(id)) {
      throw new AlreadyExistsException("ProductCategory", name);
    }
    return ProductCategoryResponse.fromEntity(productCategoryService.update(category, request));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
    ProductCategory category = productCategoryService.findById(id, user.getCompanyId())
        .orElseThrow(() -> new NotFoundException("ProductCategory", id));
    productCategoryService.delete(category);
  }
}
