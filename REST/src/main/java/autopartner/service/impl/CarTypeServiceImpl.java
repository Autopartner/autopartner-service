package autopartner.service.impl;

import autopartner.domain.entity.CarType;
import autopartner.repository.CarTypeRepository;
import autopartner.service.CarTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Repository
@Transactional
@Service("carTypeService")
public class CarTypeServiceImpl implements CarTypeService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CarTypeRepository carTypeRepository;

    @Override
    public Iterable<CarType> getByActiveTrue() {
        return carTypeRepository.findByActiveTrue();
    }

    @Override
    public CarType getCarTypeById(Integer id) {
        return carTypeRepository.findOne(id);
    }

    @Override
    public CarType saveCarType(CarType carType) {
        return carTypeRepository.save(carType);
    }
}
