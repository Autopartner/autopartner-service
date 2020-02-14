package autopartner.repository;

import autopartner.domain.entity.CarModel;
import org.springframework.data.repository.CrudRepository;

public interface CarModelRepository extends CrudRepository<CarModel, Long> {
    Iterable<CarModel> findByActiveTrue();
}