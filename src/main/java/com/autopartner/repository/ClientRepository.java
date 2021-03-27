package com.autopartner.repository;

import com.autopartner.domain.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {

  Iterable<Client> findByActiveTrue();
}
