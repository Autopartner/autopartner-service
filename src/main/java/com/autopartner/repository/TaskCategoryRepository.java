package com.autopartner.repository;

import com.autopartner.domain.TaskCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TaskCategoryRepository extends CrudRepository<TaskCategory, Long> {

  @Query(value = "select * from task_categories where company_id=? and active=true", nativeQuery = true)
  List<TaskCategory> findAll(Long companyId);

  @Query(value = "select * from task_categories where id=?1 and company_id=?2 and active=true", nativeQuery = true)
  Optional<TaskCategory> findById(Long id, Long companyId);

  @Query(value = "select id from task_categories where name=?1 and company_id=?2 and active=true", nativeQuery = true)
  Optional<Long> findIdByName(String name, Long companyId);
}
