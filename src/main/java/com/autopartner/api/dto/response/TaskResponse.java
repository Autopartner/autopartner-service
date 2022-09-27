package com.autopartner.api.dto.response;

import com.autopartner.domain.Task;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskResponse {

  String name;
  TaskCategoryResponse taskCategory;
  Integer price;

  public static TaskResponse fromEntity(Task task) {
    return TaskResponse.builder()
        .name(task.getName())
        .taskCategory(TaskCategoryResponse.fromEntity(task.getTaskCategory()))
        .price(task.getPrice())
        .build();
  }

}
