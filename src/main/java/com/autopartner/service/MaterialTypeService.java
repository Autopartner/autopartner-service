package com.autopartner.service;

import com.autopartner.domain.MaterialType;

public interface MaterialTypeService {

  Iterable<MaterialType> getByActiveTrue();

  MaterialType getMaterialTypeById(Long id);

  MaterialType saveMaterialType(MaterialType materialType);

}
