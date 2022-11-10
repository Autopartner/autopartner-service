package com.autopartner.service;

import com.autopartner.api.dto.request.ProductCategoryRequest;
import com.autopartner.domain.ProductCategory;
import java.util.List;
import java.util.Optional;

public interface ProductCategoryService {

  List<ProductCategory> findAll(Long companyId);

  Optional<ProductCategory> findById(Long id, Long companyId);

  void delete(ProductCategory category);

  ProductCategory create(ProductCategoryRequest request, Long companyId);

  ProductCategory update(ProductCategory category, ProductCategoryRequest request);

  Optional<Long> findIdByName(String name, Long companyId);

}
