package com.autopartner.api.dto.request;

public class TaskRequestFixture {

  public static TaskRequest createTaskRequest() {
    return TaskRequest.builder()
        .name("task")
        .taskCategoryId(2L)
        .price(100)
        .build();
  }

}
