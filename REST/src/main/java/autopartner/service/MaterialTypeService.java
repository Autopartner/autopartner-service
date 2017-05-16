package autopartner.service;

import autopartner.domain.entity.MaterialType;

public interface MaterialTypeService {

    Iterable<MaterialType> getByActiveTrue();

    MaterialType getMaterialTypeById(Integer id);

    MaterialType saveMaterialType(MaterialType materialType);

}
