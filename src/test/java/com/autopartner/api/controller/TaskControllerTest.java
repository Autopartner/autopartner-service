package com.autopartner.api.controller;

import static com.autopartner.api.configuration.WebConfiguration.UNAUTHORIZED_RESPONSE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.autopartner.api.dto.request.TaskRequest;
import com.autopartner.api.dto.request.TaskRequestFixture;
import com.autopartner.api.dto.response.ErrorResponse;
import com.autopartner.api.dto.response.TaskResponse;
import com.autopartner.domain.Task;
import com.autopartner.domain.TaskCategory;
import com.autopartner.domain.TaskCategoryFixture;
import com.autopartner.domain.TaskFixture;
import com.autopartner.service.TaskCategoryService;
import com.autopartner.service.TaskService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = TaskController.class)
@AutoConfigureMockMvc
public class TaskControllerTest extends AbstractControllerTest{

  public static final String URL = "/api/v1/tasks";

  @MockBean
  TaskService taskService;

  @MockBean
  TaskCategoryService categoryService;

  @Test
  void getAll_NotAuthorized_ReturnsClientError() throws Exception {
    this.mockMvc.perform(get(URL))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(UNAUTHORIZED_RESPONSE)));
  }

  @Test
  void getAll_Authorized_ReturnsTasks() throws Exception {
    Task task = TaskFixture.createTask();
    List<TaskResponse> responses = List.of(TaskResponse.fromEntity(task));
    when(taskService.findAll()).thenReturn(List.of(task));
    this.mockMvc.perform(auth(get(URL)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(responses)));
  }

  @Test
  void get_ValidTaskId_ReturnsTask() throws Exception {
    Task task = TaskFixture.createTask();
    TaskResponse response = TaskResponse.fromEntity(task);
    Long id = 1L;
    when(taskService.findById(id)).thenReturn(Optional.of(task));
    this.mockMvc.perform(auth(get(URL + "/" + id)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void get_InvalidTaskId_ReturnsError() throws Exception {
    long id = 1L;
    when(taskService.findById(4L)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "Task with id=1 is not found");
    this.mockMvc.perform(auth(get(URL + "/" + id)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void create_ValidRequest_CreatesTask() throws Exception {
    Task task = TaskFixture.createTask();
    TaskCategory category = TaskCategoryFixture.createTaskCategory();
    TaskRequest request = TaskRequestFixture.createTaskRequest();
    TaskResponse response = TaskResponse.fromEntity(task);
    when(categoryService.findById(request.getTaskCategoryId())).thenReturn(Optional.ofNullable(category));
    when(taskService.create(request, category, task.getCompanyId())).thenReturn(task);
    this.mockMvc.perform(auth(post(URL))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void update_InvalidTaskId_ReturnsError() throws Exception {
    TaskRequest request = TaskRequestFixture.createTaskRequest();
    long id = 1L;
    when(taskService.findById(id)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "Task with id=1 is not found");
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_ValidRequest_UpdatesTask() throws Exception {
    Task task = TaskFixture.createTask();
    TaskCategory category = TaskCategoryFixture.createTaskCategory();
    TaskRequest request = TaskRequestFixture.createTaskRequest();
    TaskResponse response = TaskResponse.fromEntity(task);
    long id = 1L;
    when(categoryService.findById(category.getId())).thenReturn(Optional.of(category));
    when(taskService.findById(id)).thenReturn(Optional.of(task));
    when(taskService.update(task, category, request)).thenReturn(task);
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void delete_InvalidTaskId_ReturnsError() throws Exception {
    TaskRequest request = TaskRequestFixture.createTaskRequest();
    long id = 1L;
    when(taskService.findById(id)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "Task with id=1 is not found");
    this.mockMvc.perform(auth(delete(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void delete_ValidRequest_DeletesTask() throws Exception {
    Task task = TaskFixture.createTask();
    long id = 1L;
    when(taskService.findById(id)).thenReturn(Optional.of(task));
    this.mockMvc.perform(auth(delete(URL + "/" + id)))
        .andExpect(status().is2xxSuccessful());
  }

}
