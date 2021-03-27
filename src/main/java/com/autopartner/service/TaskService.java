package com.autopartner.service;

import com.autopartner.domain.Task;

public interface TaskService {

  Iterable<Task> getByActiveTrue();

  Task getTaskById(Long id);

  Task saveTask(Task task);

}
