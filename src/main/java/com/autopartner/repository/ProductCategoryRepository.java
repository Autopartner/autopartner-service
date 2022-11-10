package com.autopartner.repository;

import com.autopartner.domain.ProductCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Long> {

  @Query(value = "select * from product_categories where company_id= :companyId and active=true", nativeQuery = true)
  List<ProductCategory> findAll(@Param("companyId") Long companyId);

  @Query(value = "select * from product_categories where id= :id and company_id= :companyId and active=true", nativeQuery = true)
  Optional<ProductCategory> findById(@Param("id") Long id, @Param("companyId") Long companyId);

  @Query(value = "select id from product_categories where name= :name and company_id= :companyId and active=true", nativeQuery = true)
  Optional<Long> findIdByName(@Param("name") String name, @Param("companyId") Long companyId);
}
