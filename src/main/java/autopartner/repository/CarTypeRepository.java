package autopartner.repository;

import autopartner.domain.entity.CarType;
import org.springframework.data.repository.CrudRepository;

public interface CarTypeRepository extends CrudRepository<CarType, Long> {
    Iterable<CarType> findByActiveTrue();
}