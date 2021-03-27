package com.autopartner.service.impl;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.domain.TaskType;
import com.autopartner.repository.TaskTypeRepository;
import com.autopartner.service.TaskTypeService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Transactional
@Service("TaskTypeService")
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
