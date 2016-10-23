package autopartner.repository;

import autopartner.domain.entity.CarBrand;
import autopartner.domain.entity.CarType;
import org.springframework.data.repository.CrudRepository;

public interface CarBrandRepository extends CrudRepository<CarBrand, Integer> {
    Iterable<CarBrand> findByActiveTrue();
}