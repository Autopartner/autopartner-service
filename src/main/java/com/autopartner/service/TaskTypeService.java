package com.autopartner.service;

import com.autopartner.domain.TaskType;

public interface TaskTypeService {

  Iterable<TaskType> getByActiveTrue();

  TaskType getTaskTypeById(Long id);

  TaskType saveTaskType(TaskType taskType);

}
