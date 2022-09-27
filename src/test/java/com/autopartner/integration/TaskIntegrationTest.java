package com.autopartner.integration;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.autopartner.api.dto.request.TaskRequest;
import com.autopartner.api.dto.request.TaskRequestFixture;
import com.autopartner.domain.Task;
import com.autopartner.domain.TaskCategory;
import com.autopartner.domain.TaskCategoryFixture;
import com.autopartner.domain.TaskFixture;
import com.autopartner.repository.TaskCategoryRepository;
import com.autopartner.repository.TaskRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskIntegrationTest extends AbstractIntegrationTest{

  public static final String TASKS_URL = "/api/v1/tasks";

  @Autowired
  TaskRepository taskRepository;

  @Autowired
  TaskCategoryRepository categoryRepository;

  @Override
  @BeforeEach
  void setUp() throws Exception {
    super.setUp();
    taskRepository.deleteAll();
  }

  @Test
  void givenTaskRequest_whenCreateTask_thenReturnSavedTaskResponse() throws Exception {
    TaskCategory category = TaskCategoryFixture.createTaskCategory();
    categoryRepository.save(category);
    TaskRequest request = TaskRequestFixture.createTaskRequest();

    ResultActions result = mockMvc.perform(post(TASKS_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    result
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(request.getName())))
        .andExpect(jsonPath("$.price", is(request.getPrice())));
  }

  @Test
  void givenDuplicatedTaskName_whenCreateTask_thenReturn400() throws Exception {
    TaskCategory category = TaskCategoryFixture.createTaskCategory();
    categoryRepository.save(category);
    taskRepository.save(TaskFixture.createTask());
    TaskRequest request = TaskRequestFixture.createTaskRequest();

    ResultActions result = mockMvc.perform(post(TASKS_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(request)));

    result
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", is("Task with param: task already exists")));
  }

  @Test
  public void givenListOfTasks_whenGetAllTasks_thenReturnTasks() throws Exception{
    TaskCategory category = TaskCategoryFixture.createTaskCategory();
    categoryRepository.save(category);
    List<Task> tasks = new ArrayList<>();
    tasks.add(TaskFixture.createTask());
    tasks.add(TaskFixture.createTask());
    taskRepository.saveAll(tasks);

    ResultActions response = mockMvc.perform(get(TASKS_URL)
        .header(HttpHeaders.AUTHORIZATION, token));

    response.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.size()", is(tasks.size())));
  }

  @Test
  public void givenTaskId_whenGetTaskById_thenReturnTaskResponse() throws Exception{
    TaskCategory category = TaskCategoryFixture.createTaskCategory();
    categoryRepository.save(category);
    Task task = taskRepository.save(TaskFixture.createTask());

    ResultActions response = mockMvc.perform(get(TASKS_URL + "/{id}", task.getId())
        .header(HttpHeaders.AUTHORIZATION, token));

    response
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(task.getName())))
        .andExpect(jsonPath("$.price", is(task.getPrice())));
  }

  @Test
  public void givenInvalidTaskId_whenGetTaskById_thenReturn404() throws Exception{
    TaskCategory category = TaskCategoryFixture.createTaskCategory();
    categoryRepository.save(category);
    Task task = taskRepository.save(TaskFixture.createTask());
    long id = task.getId() + 100;

    ResultActions response = mockMvc.perform(get(TASKS_URL + "/{id}", id)
        .header(HttpHeaders.AUTHORIZATION, token));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("Task with id=" + id +" is not found")));
  }

  @Test
  public void givenUpdatedTakRequest_whenUpdateTask_thenReturnUpdateTaskResponse() throws Exception {
    TaskCategory category = TaskCategoryFixture.createTaskCategory();
    categoryRepository.save(category);
    Task task = TaskFixture.createTask();
    taskRepository.save(task);

    TaskRequest taskRequest = TaskRequest.builder()
        .name("New")
        .price(200)
        .taskCategoryId(2L)
        .build();

    ResultActions response = mockMvc.perform(put(TASKS_URL + "/{id}", task.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(taskRequest)));

    response
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(taskRequest.getName())))
        .andExpect(jsonPath("$.price", is(taskRequest.getPrice())));
  }

  @Test
  public void givenInvalidTaskId_whenUpdateTask_thenReturn404() throws Exception {
    TaskCategory category = TaskCategoryFixture.createTaskCategory();
    categoryRepository.save(category);
    Task task = TaskFixture.createTask();
    taskRepository.save(task);
    long id = task.getId() + 100;

    TaskRequest taskRequest = TaskRequest.builder()
        .name("New")
        .price(200)
        .taskCategoryId(2L)
        .build();

    ResultActions response = mockMvc.perform(put(TASKS_URL + "/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, token)
        .content(objectMapper.writeValueAsString(taskRequest)));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("Task with id=" + id +" is not found")));
  }

  @Test
  void givenTaskId_whenDeleteTask_thenReturn200() throws Exception {
    TaskCategory category = TaskCategoryFixture.createTaskCategory();
    categoryRepository.save(category);
    Task task = taskRepository.save(TaskFixture.createTask());

    ResultActions result = mockMvc.perform(delete(TASKS_URL + "/{id}", task.getId())
        .header(HttpHeaders.AUTHORIZATION, token));

    result
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void givenInvalidTaskId_whenDeleteTask_thenReturn404() throws Exception {
    TaskCategory category = TaskCategoryFixture.createTaskCategory();
    categoryRepository.save(category);
    Task task = taskRepository.save(TaskFixture.createTask());
    long id = task.getId() + 100;

    ResultActions response = mockMvc.perform(delete(TASKS_URL + "/{id}", id)
        .header(HttpHeaders.AUTHORIZATION, token));

    response
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", startsWith("Task with id=" + id +" is not found")));
  }

}
