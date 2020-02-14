package autopartner.service;

import autopartner.domain.entity.CarType;

public interface CarTypeService {

    Iterable<CarType> getByActiveTrue();

    CarType getCarTypeById(Long id);

    CarType saveCarType(CarType carType);

}
