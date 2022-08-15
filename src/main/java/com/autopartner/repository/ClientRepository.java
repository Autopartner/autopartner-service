package com.autopartner.repository;

import com.autopartner.domain.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {

    List<Client> findByActiveTrue();

    Optional<Client> findByIdAndActiveTrue(Long id);

    boolean existsByPhoneAndActiveTrue(String phone);
}
