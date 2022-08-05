package com.autopartner.repository;

import com.autopartner.domain.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
    List<Company> findAllByActiveTrue();
    Optional<Company> findByIdAndActiveTrue(Long id);
}
