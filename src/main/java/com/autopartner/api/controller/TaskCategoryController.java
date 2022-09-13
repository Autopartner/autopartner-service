package com.autopartner.api.controller;

import com.autopartner.api.dto.request.TaskCategoryRequest;
import com.autopartner.api.dto.response.TaskCategoryResponse;
import com.autopartner.domain.TaskCategory;
import com.autopartner.domain.User;
import com.autopartner.exception.AlreadyExistsException;
import com.autopartner.exception.NotFoundException;
import com.autopartner.service.TaskCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RestController
@RequestMapping("/api/v1/task-categories")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TaskCategoryController {

  TaskCategoryService taskCategoryService;

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @GetMapping
  public List<TaskCategoryResponse> getAll() {
    return taskCategoryService.findAll().stream()
        .map(TaskCategoryResponse::fromEntity)
        .collect(Collectors.toList());
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @GetMapping(value = "/{id}")
  public TaskCategoryResponse get(@PathVariable Long id) {
    return taskCategoryService
        .findById(id)
        .map(TaskCategoryResponse::fromEntity)
        .orElseThrow(() -> new NotFoundException("TaskCategory", id));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @PostMapping
  public TaskCategoryResponse create(
      @Valid @RequestBody TaskCategoryRequest request, @AuthenticationPrincipal User user) {
    log.error("Received task category registration request {}", request);
    String name = request.getName();
    if (taskCategoryService.existsByName(name)) {
      throw new AlreadyExistsException("TaskCategory", name);
    }
    TaskCategory category = taskCategoryService.create(request, user.getCompanyId());
    log.info("Created new task category {}", request.getName());
    return TaskCategoryResponse.fromEntity(category);
  }

  @PutMapping("/{id}")
  @Secured("ROLE_USER")
  public TaskCategoryResponse update(
      @PathVariable Long id, @RequestBody @Valid TaskCategoryRequest request) {
    TaskCategory category = taskCategoryService.findById(id)
            .orElseThrow(() -> new NotFoundException("TaskCategory", id));
    String name = request.getName();
    if (taskCategoryService.existsByName(name)) {
      throw new AlreadyExistsException("TaskCategory", name);
    }
    return TaskCategoryResponse.fromEntity(taskCategoryService.update(category, request));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable Long id) {
    TaskCategory category = taskCategoryService.findById(id)
            .orElseThrow(() -> new NotFoundException("TaskCategory", id));
    taskCategoryService.delete(category);
  }
}
