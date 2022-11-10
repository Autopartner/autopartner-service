package com.autopartner.api.dto.request;

import static lombok.AccessLevel.PRIVATE;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class ProductCategoryRequest {

  @NotNull
  @Size(max = 255)
  String name;

  Long parentId;

}
