package autopartner.service;

import autopartner.domain.entity.Task;

public interface TaskService {

    Iterable<Task> getByActiveTrue();

    Task getTaskById(Integer id);

    Task saveTask(Task task);

}
