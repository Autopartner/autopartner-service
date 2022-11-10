package com.autopartner.api.dto.request;

public class ProductCategoryRequestFixture {

  public static ProductCategoryRequest createProductCategoryRequest(){
    return ProductCategoryRequest.builder()
        .name("ProductCategory")
        .parentId(1L)
        .build();
  }
  public static ProductCategoryRequest createProductCategoryRequestWithoutParent(){
    return ProductCategoryRequest.builder()
        .name("ProductCategory")
        .build();
  }

}
