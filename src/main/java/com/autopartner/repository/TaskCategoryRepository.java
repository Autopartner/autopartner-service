package com.autopartner.repository;

import com.autopartner.domain.TaskCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskCategoryRepository extends CrudRepository<TaskCategory, Long> {

  @Query(value = "select * from task_categories where company_id= :companyId and active=true", nativeQuery = true)
  List<TaskCategory> findAll(@Param("companyId") Long companyId);

  @Query(value = "select * from task_categories where id= :id and company_id= :companyId and active=true", nativeQuery = true)
  Optional<TaskCategory> findById(@Param("id") Long id, @Param("companyId") Long companyId);

  @Query(value = "select id from task_categories where name= :name and company_id= :companyId and active=true", nativeQuery = true)
  Optional<Long> findIdByName(@Param("name") String name, @Param("companyId") Long companyId);
}
