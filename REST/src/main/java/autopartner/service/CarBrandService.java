package autopartner.service;

import autopartner.domain.entity.CarBrand;

public interface CarBrandService {

    Iterable<CarBrand> getByActiveTrue();

    CarBrand getCarBrandById(Integer id);

    CarBrand saveCarBrand(CarBrand carBrand);

}
