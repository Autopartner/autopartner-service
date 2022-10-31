package com.autopartner.repository;

import com.autopartner.domain.Task;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends CrudRepository<Task, Long> {

  @Query(value = "select * from tasks where company_id= :companyId and active=true", nativeQuery = true)
  List<Task> findAll(@Param("companyId") Long companyId);

  @Query(value = "select * from tasks where category_id= :categoryId and company_id= :companyId and active=true", nativeQuery = true)
  List<Task> findAllByCategoryId(@Param("categoryId") Long categoryId, @Param("companyId") Long companyId);

  @Query(value = "select * from tasks where id= :id and company_id= :companyId and active=true", nativeQuery = true)
  Optional<Task> findById(@Param("id") Long id, @Param("companyId") Long companyId);

  @Query(value = "select id from tasks where name= :name and category_id = :categoryId and company_id= :companyId and active=true", nativeQuery = true)
  Optional<Long> findIdByCategoryIdAndName(@Param("name") String name, @Param("categoryId") Long categoryId, @Param("companyId") Long companyId);
}
