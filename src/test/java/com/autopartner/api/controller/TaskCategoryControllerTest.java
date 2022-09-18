package com.autopartner.api.controller;

import com.autopartner.api.dto.request.ClientRequest;
import com.autopartner.api.dto.request.ClientRequestFixture;
import com.autopartner.api.dto.request.TaskCategoryRequest;
import com.autopartner.api.dto.request.TaskCategoryRequestFixture;
import com.autopartner.api.dto.response.ErrorResponse;
import com.autopartner.api.dto.response.TaskCategoryResponse;
import com.autopartner.domain.TaskCategory;
import com.autopartner.domain.TaskCategoryFixture;
import com.autopartner.service.TaskCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import static com.autopartner.api.configuration.WebConfiguration.UNAUTHORIZED_RESPONSE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TaskCategoryController.class)
@AutoConfigureMockMvc
public class TaskCategoryControllerTest extends AbstractControllerTest {
  public static final String URL = "/api/v1/task-categories";

  @MockBean
  TaskCategoryService taskCategoryService;

  @Test
  void getAll_NotAuthorized_ReturnsClientError() throws Exception {
    this.mockMvc.perform(get(URL))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(UNAUTHORIZED_RESPONSE)));
  }

  @Test
  void getAll_Authorized_ReturnsClients() throws Exception {
    TaskCategory taskCategory = TaskCategoryFixture.createTaskCategory();
    TaskCategoryResponse response = TaskCategoryResponse.fromEntity(taskCategory);
    List<TaskCategoryResponse> responses = List.of(response);
    when(taskCategoryService.findAll()).thenReturn(List.of(taskCategory));
    this.mockMvc.perform(auth(get(URL)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(responses)));
  }

  @Test
  void get_ValidCompanyId_ReturnsClient() throws Exception {
    TaskCategory taskCategory = TaskCategoryFixture.createTaskCategory();
    TaskCategoryResponse response = TaskCategoryResponse.fromEntity(taskCategory);
    long id = 1L;
    when(taskCategoryService.findById(id)).thenReturn(Optional.of(taskCategory));
    this.mockMvc.perform(auth(get(URL + "/" + id)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void get_InvalidClientId_ReturnsError() throws Exception {
    long id = 1L;
    when(taskCategoryService.findById(5L)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "TaskCategory with id=1 is not found");
    this.mockMvc.perform(auth(get(URL + "/" + id)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void create_ClientAlreadyExists_ReturnsError() throws Exception {
    TaskCategoryRequest request = TaskCategoryRequestFixture.createTaskCategoryRequest();
    when(taskCategoryService.findIdByName(request.getName())).thenReturn(Optional.of(1L));
    ErrorResponse errorResponse = new ErrorResponse(400, 402, "TaskCategory with param: TaskCategory already exists");
    this.mockMvc.perform(auth(post(URL))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void create_ValidRequest_CreatesClient() throws Exception {
    TaskCategory taskCategory = TaskCategoryFixture.createTaskCategory();
    TaskCategoryRequest request = TaskCategoryRequestFixture.createTaskCategoryRequest();
    TaskCategoryResponse response = TaskCategoryResponse.fromEntity(taskCategory);
    when(taskCategoryService.findIdByName(request.getName())).thenReturn(Optional.empty());
    when(taskCategoryService.create(request, taskCategory.getCompanyId())).thenReturn(taskCategory);
    this.mockMvc.perform(auth(post(URL))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void update_InvalidClientId_ReturnsError() throws Exception {
    TaskCategoryRequest request = TaskCategoryRequestFixture.createTaskCategoryRequest();
    long id = 1L;
    when(taskCategoryService.findById(id)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "TaskCategory with id=1 is not found");
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_ClientAlreadyExists_ReturnsError() throws Exception {
    TaskCategory taskCategory = TaskCategoryFixture.createTaskCategory();
    TaskCategoryRequest request = TaskCategoryRequestFixture.createTaskCategoryRequest();
    long id = 1L;
    when(taskCategoryService.findById(id)).thenReturn(Optional.of(taskCategory));
    when(taskCategoryService.findIdByName(request.getName())).thenReturn(Optional.of(12L));
    ErrorResponse errorResponse = new ErrorResponse(400, 402, "TaskCategory with param: TaskCategory already exists");
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_ValidRequest_UpdatesClient() throws Exception {
    TaskCategory taskCategory = TaskCategoryFixture.createTaskCategory();
    TaskCategoryRequest request = TaskCategoryRequestFixture.createTaskCategoryRequest();
    TaskCategoryResponse response = TaskCategoryResponse.fromEntity(taskCategory);
    long id = 1L;
    when(taskCategoryService.findById(id)).thenReturn(Optional.of(taskCategory));
    when(taskCategoryService.update(taskCategory, request)).thenReturn(taskCategory);
    this.mockMvc.perform(auth(put(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(response)));
  }

  @Test
  void delete_InvalidClientId_ReturnsError() throws Exception {
    ClientRequest request = ClientRequestFixture.createClientRequest();
    long id = 1L;
    when(taskCategoryService.findById(id)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "TaskCategory with id=1 is not found");
    this.mockMvc.perform(auth(delete(URL + "/" + id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().is4xxClientError())
        .andExpect(content()
            .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void delete_ValidRequest_DeletesClient() throws Exception {
    TaskCategory taskCategory = TaskCategoryFixture.createTaskCategory();
    long id = 1L;
    when(taskCategoryService.findById(id)).thenReturn(Optional.of(taskCategory));
    this.mockMvc.perform(auth(delete(URL + "/" + id)))
        .andExpect(status().is2xxSuccessful());
  }
}
