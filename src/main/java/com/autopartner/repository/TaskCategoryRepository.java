package com.autopartner.repository;

import com.autopartner.domain.TaskCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TaskCategoryRepository extends CrudRepository<TaskCategory, Long> {

  List<TaskCategory> findByActiveTrue();

  Optional<TaskCategory> findByIdAndActiveTrue(Long id);

  boolean existsByNameAndActiveTrue(String name);
}
