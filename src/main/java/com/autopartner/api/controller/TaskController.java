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
  public List<TaskResponse> getAll(@AuthenticationPrincipal User user) {
    return taskService.findAll(user.getCompanyId()).stream()
        .map(TaskResponse::fromEntity)
        .toList();
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @GetMapping(value = "/{id}")
  public TaskResponse get(@PathVariable Long id, @AuthenticationPrincipal User user) {
    return taskService.findById(id, user.getCompanyId())
        .map(TaskResponse::fromEntity)
        .orElseThrow(() -> new NotFoundException("Task", id));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @GetMapping(value = "api/v1/tasks?categoryId=categoryId")
  public List<TaskResponse> getByCategory(@RequestParam Long categoryId, @AuthenticationPrincipal User user) {
    return taskService.findAllByCategory(categoryId, user.getCompanyId()).stream()
        .map(TaskResponse::fromEntity)
        .toList();
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @PostMapping
  public TaskResponse create(@Valid @RequestBody TaskRequest request,
      @AuthenticationPrincipal User user) {

    Long companyId = user.getCompanyId();
    log.info("CompanyId: {}, received task registration request {}", companyId, request);

    Long categoryId = request.getCategoryId();
    TaskCategory category = taskCategoryService.findById(categoryId, companyId)
        .orElseThrow(() -> new NotFoundException("TaskCategory", categoryId));

    String name = request.getName();
    if (taskService.findByCategoryIdAndName(name, categoryId, companyId).isPresent()) {
      throw new AlreadyExistsException("Task", name);
    }

    Task newTask = taskService.create(request, category, companyId);
    log.info("CompanyId: {}, Created new task {}", companyId, name);
    return TaskResponse.fromEntity(newTask);
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @PutMapping(value = "/{id}")
  public TaskResponse update(@PathVariable Long id,
      @Valid @RequestBody TaskRequest request, @AuthenticationPrincipal User user) {

    Long companyId = user.getCompanyId();
    Long categoryId = request.getCategoryId();

    Task task = taskService.findById(id, companyId)
        .orElseThrow(() -> new NotFoundException("Task", id));
    TaskCategory category = taskCategoryService.findById(categoryId, companyId)
        .orElseThrow(() -> new NotFoundException("Task category", request.getCategoryId()));

    String name = request.getName();
    if (taskService.findByCategoryIdAndName(name, categoryId, companyId).isPresent()) {
      throw new AlreadyExistsException("Task", name);
    }

    return TaskResponse.fromEntity(taskService.update(task, category, request));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
    Task task = taskService.findById(id, user.getCompanyId())
        .orElseThrow(() -> new NotFoundException("Task", id));
    taskService.delete(task);
  }
}
