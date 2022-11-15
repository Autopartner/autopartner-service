package com.autopartner.integration;

import com.autopartner.api.dto.request.TaskCategoryRequest;
import com.autopartner.api.dto.request.TaskCategoryRequestFixture;
import com.autopartner.domain.TaskCategory;
import com.autopartner.domain.TaskCategoryFixture;
import com.autopartner.repository.TaskCategoryRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskCategoryIntegrationTest extends AbstractIntegrationTest {
  public static final String TASK_CATEGORY_URL = "/api/v1/task-categories";
  @Autowired
  TaskCategoryRepository taskCategoryRepository;

  @Override
  @BeforeEach
  void setUp() throws Exception {
    super.setUp();
    taskCategoryRepository.deleteAll();
  }

  @Test
  void givenTaskCategoryRequest_whenCreateTaskCategory_thenReturnSavedTaskCategory() throws Exception {
    TaskCategoryRequest taskCategoryRequest = TaskCategoryRequestFixture.createTaskCategoryRequestWithoutParent();

    ResultActions result = mockMvc.perform(post(TASK_CATEGORY_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(taskCategoryRequest)));

    result
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.name",
            is(taskCategoryRequest.getName())));
  }

  @Test
  void givenDuplicatedTaskCategoryName_whenCreateTaskCategory_thenReturn400() throws Exception {

    taskCategoryRepository.save(TaskCategoryFixture.createTaskCategoryWithoutParent());
    TaskCategoryRequest taskCategoryRequest = TaskCategoryRequestFixture.createTaskCategoryRequestWithoutParent();

    ResultActions result = mockMvc.perform(post(TASK_CATEGORY_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(taskCategoryRequest)));

    result
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", is("TaskCategory with param: TaskCategory already exists")));
  }

  @Test
  public void givenListOfTaskCategories_whenGetAllTaskCategories_thenReturnTaskCategories() throws Exception {

    List<TaskCategory> taskCategories = new ArrayList<>();
    taskCategories.add(TaskCategoryFixture.createTaskCategory());
    taskCategories.add(TaskCategoryFixture.createTaskCategoryWithDifferentName());
    taskCategoryRepository.saveAll(taskCategories);

    ResultActions response = mockMvc.perform(get(TASK_CATEGORY_URL)
        .header(HttpHeaders.AUTHORIZATION, token));

    response.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.size()",
            is(taskCategories.size())));
  }

  @Test
  public void givenClientId_whenGetTaskCategoryById_thenReturnTaskCategoryResponse() throws Exception {

    TaskCategory taskCategory = taskCategoryRepository.save(TaskCategoryFixture.createTaskCategory());

    ResultActions response = mockMvc.perform(get(TASK_CATEGORY_URL + "/{id}", taskCategory.getId())
        .header(HttpHeaders.AUTHORIZATION, token));

    response
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(taskCategory.getName())))
        .andExpect(jsonPath("$.parentId", is(taskCategory.getParentId()), Long.class));

  }

  @Test
  public void givenInvalidTaskCategoryId_whenGetTaskCategoryById_thenReturn404() throws Exception {

    TaskCategory taskCategory = taskCategoryRepository.save(TaskCategoryFixture.createTaskCategory());
    long id = taskCategory.getId() + 100;

    ResultActions response = mockMvc.perform(get(TASK_CATEGORY_URL + "/{id}", id)
        .header(HttpHeaders.AUTHORIZATION, token));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("TaskCategory with id=" + id + " is not found")));
  }

  @Test
  public void givenUpdatedTaskCategoryRequest_whenUpdateTaskCategory_thenReturnUpdateTaskCategoryResponse() throws Exception{

    TaskCategory parentCategory = taskCategoryRepository.save(TaskCategoryFixture.createParentCategory());
    taskCategoryRepository.save(parentCategory);

    TaskCategory taskCategory = taskCategoryRepository.save(TaskCategoryFixture.createTaskCategoryWithoutParent());
    taskCategoryRepository.save(taskCategory);

    TaskCategoryRequest request = TaskCategoryRequest.builder()
        .name("Re Name")
        .parentId(parentCategory.getId())
        .build();

    ResultActions response = mockMvc.perform(put(TASK_CATEGORY_URL + "/{id}", taskCategory.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    response
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(request.getName())))
        .andExpect(jsonPath("$.parentId", is(request.getParentId()), Long.class));
  }

  @Test
  public void givenInvalidTaskCategoryId_whenUpdateTaskCategory_thenReturn404() throws Exception{

    TaskCategory taskCategory = taskCategoryRepository.save(TaskCategoryFixture.createTaskCategory());
    long id = taskCategory.getId() + 100;
    TaskCategoryRequest request = TaskCategoryRequestFixture.createTaskCategoryRequest();

    ResultActions response = mockMvc.perform(put(TASK_CATEGORY_URL + "/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("TaskCategory with id=" + id +" is not found")));
  }

  @Test
  public void givenDuplicatedTaskCategoryName_whenUpdateTaskCategory_thenReturn400() throws Exception{

    TaskCategory category1 = taskCategoryRepository.save(TaskCategoryFixture.createTaskCategoryWithoutParent());
    TaskCategory category2 = taskCategoryRepository.save(TaskCategoryFixture.createTaskCategoryWithDifferentName());
    TaskCategoryRequest request = TaskCategoryRequestFixture.createTaskCategoryRequestWithoutParent().withName(category2.getName());

    ResultActions response = mockMvc.perform(put(TASK_CATEGORY_URL + "/{id}", category1.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("TaskCategory with param: NewTaskCategory already exists")));
  }

  @Test
  public void givenEqualsId_whenUpdateProductCategory_thenReturn400() throws Exception{

    TaskCategory category = taskCategoryRepository.save(TaskCategoryFixture.createTaskCategory());
    TaskCategoryRequest request = TaskCategoryRequestFixture.createTaskCategoryRequest().withParentId(category.getId());

    ResultActions response = mockMvc.perform(put(TASK_CATEGORY_URL + "/{id}", category.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("ParentId: " + category.getId() + " equals current id: " + category.getId())));
  }

  @Test
  void givenTaskCategoryId_whenDeleteTaskCategory_thenReturn200() throws Exception {

    TaskCategory category = taskCategoryRepository.save(TaskCategoryFixture.createTaskCategory());

    ResultActions result = mockMvc.perform(delete(TASK_CATEGORY_URL + "/{id}", category.getId())
        .header(HttpHeaders.AUTHORIZATION, token));

    result
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void givenInvalidTaskCategoryId_whenDeleteTaskCategory_thenReturn404() throws Exception{

    TaskCategory category = taskCategoryRepository.save(TaskCategoryFixture.createTaskCategory());
    long id = category.getId() + 100;

    ResultActions response = mockMvc.perform(delete(TASK_CATEGORY_URL + "/{id}", id)
        .header(HttpHeaders.AUTHORIZATION, token));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("TaskCategory with id=" + id +" is not found")));
  }

}
