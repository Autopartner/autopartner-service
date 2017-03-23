package autopartner.service;

import autopartner.domain.entity.Car;

public interface CarService {

    Iterable<Car> getByActiveTrue();

    Car getCarById(Integer id);

    Car saveCar(Car car);

}
