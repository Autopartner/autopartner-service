package com.autopartner.api.controller;

import com.autopartner.domain.Task;
import com.autopartner.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

import static lombok.AccessLevel.PRIVATE;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TaskController {

  TaskService taskService;

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = {"/api/task"}, method = RequestMethod.GET)
  public ResponseEntity<Iterable<Task>> getAll() {
    return ResponseEntity.ok(taskService.getByActiveTrue());
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = "/api/task/{id}", method = RequestMethod.GET)
  public ResponseEntity<Task> get(@PathVariable Long id) {
    return ResponseEntity.ok(taskService.getTaskById(id));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = "/api/task", method = RequestMethod.POST)
  public ResponseEntity<Task> save(@Valid @RequestBody Task task) {
    return ResponseEntity.ok(taskService.saveTask(task));
  }
}
