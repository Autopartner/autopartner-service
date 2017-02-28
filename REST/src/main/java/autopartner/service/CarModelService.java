package autopartner.service;

import autopartner.domain.entity.CarModel;

public interface CarModelService {

    Iterable<CarModel> getByActiveTrue();

    CarModel getCarModelById(Integer id);

    CarModel saveCarModel(CarModel carModel);

}
