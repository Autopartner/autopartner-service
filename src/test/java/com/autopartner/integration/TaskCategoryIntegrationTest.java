package com.autopartner.integration;

import com.autopartner.api.dto.request.TaskCategoryRequest;
import com.autopartner.api.dto.request.TaskCategoryRequestFixture;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskCategoryIntegrationTest extends AbstractIntegrationTest {
  @Test
  void givenTaskCategoryRequest_whenCreateTaskCategory_thenReturnSavedTaskCategory() throws Exception {
    TaskCategoryRequest taskCategoryRequest = TaskCategoryRequestFixture.createTaskCategoryRequest();

    ResultActions result = mockMvc.perform(post("/api/v1/task-categories")
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(taskCategoryRequest)));

    result
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.name",
            is(taskCategoryRequest.getName())))
        .andExpect(jsonPath("$.parentId",
            is(taskCategoryRequest.getParentId())));
  }
}
