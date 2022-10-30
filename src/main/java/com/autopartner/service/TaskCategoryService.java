package com.autopartner.service;

import com.autopartner.api.dto.request.TaskCategoryRequest;
import com.autopartner.domain.TaskCategory;

import java.util.List;
import java.util.Optional;

public interface TaskCategoryService {

  List<TaskCategory> findAll(Long companyId);

  Optional<TaskCategory> findById(Long id, Long companyId);

  void delete(TaskCategory category);

  TaskCategory create(TaskCategoryRequest request, Long companyId);

  TaskCategory update(TaskCategory category, TaskCategoryRequest request);

  Optional<Long> findIdByName(String name, Long companyId);

}
