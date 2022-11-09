package com.autopartner.api.dto.request;

public class ProductCategoryRequestFixture {

  public static ProductCategoryRequest createProductCategoryRequest(){
    return ProductCategoryRequest.builder()
        .name("ProductCategory")
        .build();
  }

}
