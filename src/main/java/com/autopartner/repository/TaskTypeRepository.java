package com.autopartner.repository;

import com.autopartner.domain.TaskType;
import org.springframework.data.repository.CrudRepository;

public interface TaskTypeRepository extends CrudRepository<TaskType, Long> {

  Iterable<TaskType> findByActiveTrue();
}
