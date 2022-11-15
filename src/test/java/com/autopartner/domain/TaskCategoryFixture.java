package com.autopartner.domain;

public class TaskCategoryFixture {
  public static TaskCategory createTaskCategory() {
    return TaskCategory.builder()
        .id(2L)
        .companyId(1L)
        .parentId(1L)
        .name("TaskCategory")
        .active(true)
        .build();
  }

  public static TaskCategory createTaskCategoryWithDifferentName() {
    return TaskCategory.builder()
        .companyId(1L)
        .name("NewTaskCategory")
        .active(true)
        .build();
  }

  public static TaskCategory createTaskCategoryWithoutParent() {
    return TaskCategory.builder()
        .id(2L)
        .companyId(1L)
        .name("TaskCategory")
        .active(true)
        .build();
  }
  public static TaskCategory createParentCategory() {
    return TaskCategory.builder()
        .id(1L)
        .companyId(1L)
        .name("ParentCategory")
        .active(true)
        .build();
  }
}
