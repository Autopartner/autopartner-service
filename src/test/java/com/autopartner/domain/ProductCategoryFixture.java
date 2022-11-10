package com.autopartner.domain;

public class ProductCategoryFixture {

  public static ProductCategory createProductCategory() {
    return ProductCategory.builder()
        .id(2L)
        .companyId(1L)
        .name("ProductCategory")
        .parentId(1L)
        .active(true)
        .build();
  }
  public static ProductCategory createProductCategoryWithoutParent() {
    return ProductCategory.builder()
        .id(2L)
        .companyId(1L)
        .name("ProductCategory")
        .active(true)
        .build();
  }
  public static ProductCategory createParentCategory() {
    return ProductCategory.builder()
        .id(1L)
        .companyId(1L)
        .name("ParentCategory")
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
