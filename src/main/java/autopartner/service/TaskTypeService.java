package autopartner.service;

import autopartner.domain.entity.TaskType;

public interface TaskTypeService {

    Iterable<TaskType> getByActiveTrue();

    TaskType getTaskTypeById(Long id);

    TaskType saveTaskType(TaskType taskType);

}
