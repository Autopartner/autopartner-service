package autopartner.repository;

import autopartner.domain.entity.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
    Iterable<Client> findByActiveTrue();
}