package com.autopartner.service.impl;

import com.autopartner.api.dto.request.ProductCategoryRequest;
import com.autopartner.domain.ProductCategory;
import com.autopartner.repository.ProductCategoryRepository;
import com.autopartner.service.ProductCategoryService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ProductCategoryServiceImpl implements ProductCategoryService {

  ProductCategoryRepository productCategoryRepository;


  @Override
  public List<ProductCategory> findAll(Long companyId) {
    return productCategoryRepository.findAll(companyId);
  }

  @Override
  public Optional<ProductCategory> findById(Long id, Long companyId) {
    return productCategoryRepository.findById(id, companyId);
  }

  @Override
  public void delete(ProductCategory category) {
    category.delete();
    save(category);
  }

  private ProductCategory save(ProductCategory category) {
    return productCategoryRepository.save(category);
  }

  @Override
  public ProductCategory create(ProductCategoryRequest request, Long companyId) {
    return save(ProductCategory.create(request, companyId));
  }

  @Override
  public ProductCategory update(ProductCategory category, ProductCategoryRequest request) {
    category.update(request);
    return save(category);
  }

  @Override
  public Optional<Long> findIdByName(String name, Long companyId) {
    return productCategoryRepository.findIdByName(name, companyId);
  }
}
