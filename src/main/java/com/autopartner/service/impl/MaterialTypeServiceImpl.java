package com.autopartner.service.impl;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.domain.MaterialType;
import com.autopartner.repository.MaterialTypeRepository;
import com.autopartner.service.MaterialTypeService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class MaterialTypeServiceImpl implements MaterialTypeService {

  MaterialTypeRepository materialTypeRepository;

  @Override
  public Iterable<MaterialType> getByActiveTrue() {
    return materialTypeRepository.findByActiveTrue();
  }

  @Override
  public MaterialType getMaterialTypeById(Long id) {
    return materialTypeRepository.findById(id).get();
  }

  @Override
  public MaterialType saveMaterialType(MaterialType materialType) {
    return materialTypeRepository.save(materialType);
  }
}
