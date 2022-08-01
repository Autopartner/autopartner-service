package com.autopartner.repository;

import com.autopartner.domain.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
    Iterable<Company> findByActiveTrue();
    Company findCompanyByIdAndActiveTrue(Long id);
}
