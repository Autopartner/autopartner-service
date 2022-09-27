package com.autopartner.api.controller;

import com.autopartner.api.dto.request.TaskRequest;
import com.autopartner.api.dto.response.TaskResponse;
import com.autopartner.domain.Task;
import com.autopartner.domain.TaskCategory;
import com.autopartner.domain.User;
import com.autopartner.exception.AlreadyExistsException;
import com.autopartner.exception.NotFoundException;
import com.autopartner.service.TaskCategoryService;
import com.autopartner.service.TaskService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TaskController {

  TaskService taskService;
  TaskCategoryService taskCategoryService;

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @GetMapping
  public List<TaskResponse> getAll() {
    return taskService.findAll().stream()
        .map(TaskResponse::fromEntity)
        .toList();
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @GetMapping(value = "/{id}")
  public TaskResponse get(@PathVariable Long id) {
    return taskService.findById(id)
        .map(TaskResponse::fromEntity)
        .orElseThrow(() -> new NotFoundException("Task", id));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @GetMapping(value = "api/v1/tasks?categoryId=categoryId")
  public List<TaskResponse> getByCategory(@RequestParam Long categoryId) {
    return taskService.findAllByCategory(categoryId).stream()
        .map(TaskResponse::fromEntity)
        .toList();
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @PostMapping
  public TaskResponse create(@Valid @RequestBody TaskRequest request,
      @AuthenticationPrincipal User user) {
    log.info("Received task registration request {}", request);
    String name = request.getName();
    if (taskService.findByCategoryId(request.getTaskCategoryId()).isPresent() &&
        taskService.findByCategoryId(request.getTaskCategoryId()).get().getName().equals(request.getName())) {
      throw new AlreadyExistsException("Task", name);
    }
    TaskCategory category = taskCategoryService.findById(request.getTaskCategoryId())
        .orElseThrow(() -> new NotFoundException("TaskСategory", request.getTaskCategoryId()));
    Task newTask = taskService.create(request, category, user.getCompanyId());
    log.info("Created new task {}", request.getName());
    return TaskResponse.fromEntity(newTask);
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @PutMapping(value = "/{id}")
  public TaskResponse update(@PathVariable Long id,
      @Valid @RequestBody TaskRequest request) {
    Task task = taskService.findById(id)
        .orElseThrow(() -> new NotFoundException("Task", id));
    TaskCategory category = taskCategoryService.findById(request.getTaskCategoryId())
        .orElseThrow(() -> new NotFoundException("Task category", request.getTaskCategoryId()));
    return TaskResponse.fromEntity(taskService.update(task, category, request));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable Long id) {
    Task task = taskService.findById(id)
        .orElseThrow(() -> new NotFoundException("Task", id));
    taskService.delete(task);
  }
}
