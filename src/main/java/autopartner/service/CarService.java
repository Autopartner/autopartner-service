package autopartner.service;

import autopartner.domain.entity.Car;

public interface CarService {

    Iterable<Car> getByActiveTrue();

    Car getCarById(Long id);

    Car saveCar(Car car);

}
