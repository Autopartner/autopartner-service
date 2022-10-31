package com.autopartner.service.impl;

import com.autopartner.api.dto.request.TaskRequest;
import com.autopartner.domain.Task;
import com.autopartner.domain.TaskCategory;
import com.autopartner.repository.TaskRepository;
import com.autopartner.service.TaskService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TaskServiceImpl implements TaskService {

  TaskRepository taskRepository;

  @Override
  public List<Task> findAll(Long companyId) {
    return taskRepository.findAll(companyId);
  }

  @Override
  public Optional<Task> findById(Long id, Long companyId) {
    return taskRepository.findById(id, companyId);
  }

  @Override
  public List<Task> findAllByCategory(Long categoryId, Long companyId) {
    return taskRepository.findAllByCategoryId(categoryId, companyId);
  }

  @Override
  public Task create(TaskRequest request, TaskCategory category, Long companyId) {
    return save(Task.create(request, category, companyId));
  }

  private Task save(Task task) {
   return taskRepository.save(task);
  }

  @Override
  public Task update(Task task, TaskCategory category, TaskRequest request) {
    task.update(request, category);
    return save(task);
  }

  @Override
  public void delete(Task task) {
    task.delete();
    save(task);
  }

  @Override
  public Optional<Long> findIdByName(String name, Long categoryId, Long companyId) {
    return taskRepository.findIdByName(name, categoryId, companyId);
  }
}
