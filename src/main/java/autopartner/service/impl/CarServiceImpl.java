package autopartner.service.impl;

import autopartner.domain.entity.Car;
import autopartner.repository.CarRepository;
import autopartner.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Repository
@Transactional
@Service("carService")
public class CarServiceImpl implements CarService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CarRepository carRepository;

    @Override
    public Iterable<Car> getByActiveTrue() {
        return carRepository.findByActiveTrue();
    }

    @Override
    public Car getCarById(Long id) {
        return carRepository.findById(id).get();
    }

    @Override
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }
}
