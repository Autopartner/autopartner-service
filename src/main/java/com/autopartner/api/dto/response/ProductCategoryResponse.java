package com.autopartner.api.dto.response;

import com.autopartner.domain.ProductCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCategoryResponse {

  Long id;
  String name;

  Long parentId;

  public static ProductCategoryResponse fromEntity(ProductCategory category) {
    return ProductCategoryResponse.builder()
        .id(category.getId())
        .name(category.getName())
        .parentId(category.getParentId())
        .build();
  }

}
