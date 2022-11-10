package com.autopartner.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.autopartner.api.dto.request.ProductCategoryRequest;
import com.autopartner.api.dto.request.ProductCategoryRequestFixture;
import com.autopartner.domain.ProductCategory;
import com.autopartner.domain.ProductCategoryFixture;
import com.autopartner.repository.ProductCategoryRepository;
import com.autopartner.service.impl.ProductCategoryServiceImpl;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PACKAGE)
public class ProductCategoryServiceTest {

  @Mock
  ProductCategoryRepository productCategoryRepository;
  @InjectMocks
  ProductCategoryServiceImpl productCategoryService;
  @Captor
  ArgumentCaptor<ProductCategory> productCategoryArgumentCaptor;
  @Captor
  ArgumentCaptor<Long> productCategoryIdArgumentCaptor;
  @Captor
  ArgumentCaptor<Long> companyIdArgumentCaptor;

  @Test
  void findAll() {
    long companyId = 1L;
    List<ProductCategory> productCategories = List.of(ProductCategoryFixture.createProductCategory());
    when(productCategoryRepository.findAll(companyId)).thenReturn(productCategories);
    List<ProductCategory> actualProductCategories = productCategoryService.findAll(companyId);
    assertThat(productCategories).isEqualTo(actualProductCategories);
  }

  @Test
  void create() {
    ProductCategoryRequest request = ProductCategoryRequestFixture.createProductCategoryRequest();
    productCategoryService.create(request, 1L);
    verify(productCategoryRepository).save(productCategoryArgumentCaptor.capture());
    ProductCategory actualProductCategory = productCategoryArgumentCaptor.getValue();
    assertThatProductCategoryMappedCorrectly(actualProductCategory, request);
  }

  @Test
  void findById() {
    Long companyId = 1L;
    ProductCategory productCategory = ProductCategoryFixture.createProductCategory();
    when(productCategoryRepository.findById(anyLong(), anyLong()))
        .thenReturn(Optional.ofNullable(productCategory));
    productCategoryService.findById(productCategory.getId(), companyId);
    verify(productCategoryRepository).findById(productCategoryIdArgumentCaptor.capture(), companyIdArgumentCaptor.capture());
    Long currentCompanyId = companyIdArgumentCaptor.getValue();
    Long id = productCategoryIdArgumentCaptor.getValue();
    assertThat(id).isEqualTo(productCategory.getId());
    assertThat(currentCompanyId).isEqualTo(companyId);
  }

  @Test
  void update() {
    ProductCategory productCategory = ProductCategoryFixture.createProductCategory();
    ProductCategoryRequest request = ProductCategoryRequestFixture.createProductCategoryRequest();
    productCategoryService.update(productCategory, request);
    verify(productCategoryRepository).save(productCategoryArgumentCaptor.capture());
    ProductCategory actualProductCategory = productCategoryArgumentCaptor.getValue();
    assertThatProductCategoryMappedCorrectly(actualProductCategory, request);
  }

  @Test
  void delete() {
    ProductCategory productCategory = ProductCategoryFixture.createProductCategory();
    productCategoryService.delete(productCategory);
    verify(productCategoryRepository).save(productCategoryArgumentCaptor.capture());
    ProductCategory actualProductCategory = productCategoryArgumentCaptor.getValue();
    assertThat(actualProductCategory).isEqualTo(productCategory);
    assertThat(actualProductCategory.getActive()).isFalse();
  }

  private void assertThatProductCategoryMappedCorrectly(ProductCategory actualProductCategory, ProductCategoryRequest request) {
    assertThat(actualProductCategory.getName()).isEqualTo((request.getName()));
  }

}
