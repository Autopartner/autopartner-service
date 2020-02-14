package autopartner.service;

import autopartner.domain.entity.Task;

public interface TaskService {

    Iterable<Task> getByActiveTrue();

    Task getTaskById(Long id);

    Task saveTask(Task task);

}
