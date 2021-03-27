package com.autopartner.service.impl;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.domain.Task;
import com.autopartner.repository.TaskRepository;
import com.autopartner.service.TaskService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Transactional
@Service("taskService")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TaskServiceImpl implements TaskService {

  TaskRepository taskRepository;

  @Override
  public Iterable<Task> getByActiveTrue() {
    return taskRepository.findByActiveTrue();
  }

  @Override
  public Task getTaskById(Long id) {
    return taskRepository.findById(id).get();
  }

  @Override
  public Task saveTask(Task task) {
    return taskRepository.save(task);
  }
}
