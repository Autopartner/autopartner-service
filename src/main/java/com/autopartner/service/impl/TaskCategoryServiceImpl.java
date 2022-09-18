package com.autopartner.service.impl;

import com.autopartner.api.dto.request.TaskCategoryRequest;
import com.autopartner.domain.TaskCategory;
import com.autopartner.repository.TaskCategoryRepository;
import com.autopartner.service.TaskCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TaskCategoryServiceImpl implements TaskCategoryService {

  TaskCategoryRepository taskCategoryRepository;

  @Override
  public List<TaskCategory> findAll() {
    return taskCategoryRepository.findByActiveTrue();
  }

  @Override
  public Optional<TaskCategory> findById(Long id) {
    return taskCategoryRepository.findByIdAndActiveTrue(id);
  }

  @Override
  public void delete(TaskCategory category) {
    category.delete();
    save(category);
  }

  private TaskCategory save(TaskCategory category) {
    return taskCategoryRepository.save(category);
  }

  @Override
  public TaskCategory create(TaskCategoryRequest request, Long companyId) {
    return save(TaskCategory.create(request, companyId));
  }

  @Override
  public TaskCategory update(TaskCategory category, TaskCategoryRequest request) {
    category.update(request);
    return save(category);
  }

  @Override
  public Optional<Long> findIdByName(String name) {
    return taskCategoryRepository.findIdByNameAndActiveTrue(name);
  }
}
