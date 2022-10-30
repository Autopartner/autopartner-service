package com.autopartner.api.dto.response;

import com.autopartner.domain.TaskCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskCategoryResponse {
  Long id;
  String name;
  Long parentId;

  public static TaskCategoryResponse fromEntity(TaskCategory category) {
    return TaskCategoryResponse.builder()
        .id(category.getId())
        .name(category.getName())
        .parentId(category.getParentId())
        .build();
  }
}
