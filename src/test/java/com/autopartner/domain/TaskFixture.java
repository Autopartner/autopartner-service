package com.autopartner.domain;

public class TaskFixture {

  public static Task createTask() {
    return Task.builder()
        .id(1L)
        .name("task")
        .taskCategory(TaskCategoryFixture.createTaskCategory())
        .price(100)
        .active(true)
        .companyId(1L)
        .build();
  }

  public static Task createTask(TaskCategory taskCategory) {
    return Task.builder()
        .id(1L)
        .name("task")
        .taskCategory(taskCategory)
        .price(100)
        .active(true)
        .companyId(1L)
        .build();
  }
}
