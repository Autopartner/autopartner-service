package autopartner.service.impl;

import autopartner.domain.entity.CarBrand;
import autopartner.domain.entity.CarModel;
import autopartner.repository.CarBrandRepository;
import autopartner.repository.CarModelRepository;
import autopartner.service.CarBrandService;
import autopartner.service.CarModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Repository
@Transactional
@Service("carModelService")
public class CarModelServiceImpl implements CarModelService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CarModelRepository carModelRepository;

    @Override
    public Iterable<CarModel> getByActiveTrue() {
        return carModelRepository.findByActiveTrue();
    }

    @Override
    public CarModel getCarModelById(Integer id) {
        return carModelRepository.findOne(id);
    }

    @Override
    public CarModel saveCarModel(CarModel carModel) {
        return carModelRepository.save(carModel);
    }
}
