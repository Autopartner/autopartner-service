package autopartner.repository;

import autopartner.domain.entity.Material;
import org.springframework.data.repository.CrudRepository;

public interface MaterialRepository extends CrudRepository<Material, Integer> {
    Iterable<Material> findByActiveTrue();
}