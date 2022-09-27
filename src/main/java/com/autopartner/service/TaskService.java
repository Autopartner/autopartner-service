package com.autopartner.service;

import com.autopartner.api.dto.request.TaskRequest;
import com.autopartner.domain.Task;
import com.autopartner.domain.TaskCategory;
import java.util.List;
import java.util.Optional;

public interface TaskService {

  List<Task> findAll();

  Optional<Task> findById(Long id);

  List<Task> findAllByCategory(Long categoryId);

  Task create(TaskRequest request, TaskCategory category, Long companyId);

  Task update(Task task, TaskCategory category, TaskRequest request);

  Task save(Task task);

  void delete(Task task);

  Optional<Task> findByCategoryId(Long id);

}
