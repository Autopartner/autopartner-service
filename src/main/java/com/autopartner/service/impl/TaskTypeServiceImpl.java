package com.autopartner.service.impl;

import com.autopartner.domain.TaskType;
import com.autopartner.repository.TaskTypeRepository;
import com.autopartner.service.TaskTypeService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import static lombok.AccessLevel.PRIVATE;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TaskTypeServiceImpl implements TaskTypeService {

  TaskTypeRepository taskTypeRepository;

  @Override
  public Iterable<TaskType> getByActiveTrue() {
    return taskTypeRepository.findByActiveTrue();
  }

  @Override
  public TaskType getTaskTypeById(Long id) {
    return taskTypeRepository.findById(id).get();
  }

  @Override
  public TaskType saveTaskType(TaskType taskType) {
    return taskTypeRepository.save(taskType);
  }
}
