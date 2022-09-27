package com.autopartner.repository;

import com.autopartner.domain.Task;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {

  List<Task> findByActiveTrue();

  Optional<Task> findByIdAndActiveTrue(Long id);

  Optional<Task> findByTaskCategoryIdAndActiveTrue(Long id);
}
