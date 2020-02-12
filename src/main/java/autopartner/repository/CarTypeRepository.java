package autopartner.repository;

import autopartner.domain.entity.CarType;
import org.springframework.data.repository.CrudRepository;

public interface CarTypeRepository extends CrudRepository<CarType, Integer> {
    Iterable<CarType> findByActiveTrue();
}