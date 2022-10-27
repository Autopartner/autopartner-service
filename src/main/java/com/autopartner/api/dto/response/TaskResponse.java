package com.autopartner.api.dto.response;

import com.autopartner.domain.Task;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskResponse {

  String name;
  TaskCategoryResponse category;
  BigDecimal price;

  public static TaskResponse fromEntity(Task task) {
    return TaskResponse.builder()
        .name(task.getName())
        .category(TaskCategoryResponse.fromEntity(task.getCategory()))
        .price(task.getPrice())
        .build();
  }

}
