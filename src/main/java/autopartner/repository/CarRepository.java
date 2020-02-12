package autopartner.repository;

import autopartner.domain.entity.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Integer> {
    Iterable<Car> findByActiveTrue();
}