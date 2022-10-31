package com.autopartner.api.dto.request;

import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TaskRequest {

  @NotNull
  @Size(max = 255)
  String name;

  @NotNull
  @Min(value = 1)
  BigDecimal price;

  @NotNull
  Long categoryId;
}
