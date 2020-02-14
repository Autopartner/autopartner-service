package autopartner.repository;

import autopartner.domain.entity.OrderMaterial;
import org.springframework.data.repository.CrudRepository;

public interface OrderMaterialRepository extends CrudRepository<OrderMaterial, Long> {
    Iterable<OrderMaterial> findByActiveTrue();
}