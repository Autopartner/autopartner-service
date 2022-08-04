package com.autopartner.api.controller;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.domain.TaskType;
import com.autopartner.service.TaskTypeService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TaskTypeController {

  TaskTypeService taskTypeService;

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = {"/api/task/type"}, method = RequestMethod.GET)
  public ResponseEntity<Iterable<TaskType>> getAll() {
    return ResponseEntity.ok(taskTypeService.getByActiveTrue());
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = "/api/task/type/{id}", method = RequestMethod.GET)
  public ResponseEntity<TaskType> get(@PathVariable Long id) {
    return ResponseEntity.ok(taskTypeService.getTaskTypeById(id));
  }

  @Secured({"ROLE_ADMIN", "ROLE_ROOT"})
  @RequestMapping(value = "/api/task/type", method = RequestMethod.POST)
  public ResponseEntity<TaskType> save(@Valid @RequestBody TaskType taskType) {
    return ResponseEntity.ok(taskTypeService.saveTaskType(taskType));
  }
}
