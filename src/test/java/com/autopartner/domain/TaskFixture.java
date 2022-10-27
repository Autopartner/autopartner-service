package com.autopartner.domain;

import java.math.BigDecimal;

public class TaskFixture {

  public static Task createTask() {
    return Task.builder()
        .companyId(1L)
        .id(1L)
        .name("task")
        .category(TaskCategoryFixture.createTaskCategory())
        .price(new BigDecimal("100.0"))
        .active(true)
        .companyId(1L)
        .build();
  }

  public static Task createTask(TaskCategory taskCategory) {
    return Task.builder()
        .companyId(1L)
        .id(1L)
        .name("task")
        .category(taskCategory)
        .price(BigDecimal.valueOf(100))
        .active(true)
        .companyId(1L)
        .build();
  }
}
