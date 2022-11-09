package com.autopartner.domain;

public class ProductCategoryFixture {

  public static ProductCategory createProductCategory() {
    return ProductCategory.builder()
        .id(2L)
        .companyId(1L)
        .name("ProductCategory")
        .active(true)
        .build();
  }

  public static ProductCategory createProductCategoryWithDifferentName() {
    return ProductCategory.builder()
        .companyId(1L)
        .name("ProductCategory2")
        .active(true)
        .build();
  }

}
