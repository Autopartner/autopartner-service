package com.autopartner.api.dto.request;

import java.math.BigDecimal;

public class TaskRequestFixture {

  public static TaskRequest createTaskRequest() {
    return TaskRequest.builder()
        .name("task")
        .categoryId(2L)
        .price(BigDecimal.valueOf(100))
        .build();
  }

}
