package com.autopartner.service;

import com.autopartner.api.dto.request.TaskRequest;
import com.autopartner.domain.Task;
import com.autopartner.domain.TaskCategory;
import java.util.List;
import java.util.Optional;

public interface TaskService {

  List<Task> findAll(Long companyId);

  Optional<Task> findById(Long id, Long companyId);

  List<Task> findAllByCategory(Long categoryId, Long companyId);

  Task create(TaskRequest request, TaskCategory category, Long companyId);

  Task update(Task task, TaskCategory category, TaskRequest request);

  void delete(Task task);

  Optional<Long> findIdByCategoryIdAndName(String name, Long categoryId, Long companyId);

}
