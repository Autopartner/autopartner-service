package com.autopartner.repository;

import com.autopartner.domain.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {

  Iterable<Task> findByActiveTrue();
}
