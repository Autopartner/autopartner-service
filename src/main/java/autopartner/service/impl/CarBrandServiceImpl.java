package autopartner.service.impl;

import autopartner.domain.entity.CarBrand;
import autopartner.repository.CarBrandRepository;
import autopartner.service.CarBrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Repository
@Transactional
@Service("carBrandService")
public class CarBrandServiceImpl implements CarBrandService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CarBrandRepository carBrandRepository;

    @Override
    public Iterable<CarBrand> getByActiveTrue() {
        return carBrandRepository.findByActiveTrue();
    }

    @Override
    public CarBrand getCarBrandById(Integer id) {
        return carBrandRepository.findById(id).get();
    }

    @Override
    public CarBrand saveCarBrand(CarBrand carBrand) {
        return carBrandRepository.save(carBrand);
    }
}
