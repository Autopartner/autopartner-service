package com.autopartner.api.controller;

import com.autopartner.api.dto.request.UserRequest;
import com.autopartner.api.dto.response.ErrorResponse;
import com.autopartner.api.dto.response.UserResponse;
import com.autopartner.domain.User;
import com.autopartner.domain.UserFixture;
import com.autopartner.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.autopartner.api.configuration.WebConfiguration.UNAUTHORIZED_RESPONSE;
import static com.autopartner.api.dto.request.UserRequestFixture.createUserRequest;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest extends AbstractControllerTest {

  public static final String URL = "/api/v1/users";

  @MockBean
  UserService userService;

  @Test
  void getAll_NotAuthorized_ReturnsClientError() throws Exception {
    this.mockMvc.perform(get(URL))
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(objectMapper.writeValueAsString(UNAUTHORIZED_RESPONSE)));
  }

  @Test
  void getAll_Authorized_ReturnsUsers() throws Exception {
    User user = UserFixture.createUser();
    List<UserResponse> userResponses = List.of(UserResponse.fromEntity(user));
    when(userService.findAll()).thenReturn(List.of(user));
    this.mockMvc.perform(auth(get(URL)))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().string(objectMapper.writeValueAsString(userResponses)));
  }

  @Test
  void get_ValidUserId_ReturnsUser() throws Exception {
    User user = UserFixture.createUser();
    long userId = 1L;
    when(userService.findById(userId)).thenReturn(Optional.ofNullable(user));
    UserResponse userResponse = UserResponse.fromEntity(Objects.requireNonNull(user));
    this.mockMvc.perform(auth(get(URL + "/" + userId)))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().string(objectMapper.writeValueAsString(userResponse)));
  }

  @Test
  void get_InvalidUserId_ReturnsError() throws Exception {
    long userId = 1L;
    when(userService.findById(userId)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "User with id=1 is not found");
    this.mockMvc.perform(auth(get(URL + "/" + userId)))
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void create_ValidRequest_CreatesUser() throws Exception {
    UserRequest request = createUserRequest();
    when(userService.existsByEmail(request.getEmail())).thenReturn(false);
    User user = UserFixture.createUser();
    UserResponse userResponse = UserResponse.fromEntity(user);
    when(userService.create(request, user.getCompanyId())).thenReturn(user);
    this.mockMvc.perform(auth(post(URL))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content()
                    .string(objectMapper.writeValueAsString(userResponse)));
  }

  @Test
  void create_InvalidRequest_ReturnsError() throws Exception {
    UserRequest request = createUserRequest();
    when(userService.existsByEmail(request.getEmail())).thenReturn(true);
    ErrorResponse errorResponse = new ErrorResponse(400, 402, "User already exists with email: " + request.getEmail());
    this.mockMvc.perform(auth(post(URL))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_InvalidUserId_ReturnsError() throws Exception {
    UserRequest request = createUserRequest();
    long userId = 1L;
    when(userService.findById(userId)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "User with id=1 is not found");
    this.mockMvc.perform(auth(put(URL + "/" + userId))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().is4xxClientError())
            .andExpect(content()
                    .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void update_ValidRequest_UpdatesUser() throws Exception {
    UserRequest request = createUserRequest();
    User user = UserFixture.createUser();
    long userId = 1L;
    UserResponse userResponse = UserResponse.fromEntity(user);
    when(userService.findById(userId)).thenReturn(Optional.of(user));
    when(userService.update(user, request)).thenReturn(user);
    this.mockMvc.perform(auth(put(URL + "/" + userId))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content()
                    .string(objectMapper.writeValueAsString(userResponse)));
  }

  @Test
  void delete_InvalidUserId_ReturnsError() throws Exception {
    UserRequest request = createUserRequest();
    long userId = 1L;
    when(userService.findById(userId)).thenReturn(Optional.empty());
    ErrorResponse errorResponse = new ErrorResponse(404, 404, "User with id=1 is not found");
    this.mockMvc.perform(auth(delete(URL + "/" + userId))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().is4xxClientError())
            .andExpect(content()
                    .string(objectMapper.writeValueAsString(errorResponse)));
  }

  @Test
  void delete_ValidRequest_DeletesUser() throws Exception {
    User user = UserFixture.createUser();
    long userId = 1L;
    when(userService.findById(userId)).thenReturn(Optional.of(user));
    this.mockMvc.perform(auth(delete(URL + "/" + userId)))
            .andExpect(status().is2xxSuccessful());
  }

}
