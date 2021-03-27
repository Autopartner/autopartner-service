package com.autopartner.service.impl;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.domain.InputMaterial;
import com.autopartner.domain.Material;
import com.autopartner.repository.InputMaterialRepository;
import com.autopartner.repository.MaterialRepository;
import com.autopartner.service.InputMaterialService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class InputMaterialServiceImpl implements InputMaterialService {

  InputMaterialRepository inputMaterialRepository;
  MaterialRepository materialRepository;

  @Override
  public Iterable<InputMaterial> getByActiveTrue() {
    return inputMaterialRepository.findByActiveTrue();
  }

  @Override
  public InputMaterial getById(Long id) {
    return inputMaterialRepository.findById(id).get();
  }

  @Override
  public InputMaterial saveToWarehouse(InputMaterial inputMaterial) {

    Long materialId = inputMaterial.getMaterial().getId();

    Material material = materialRepository.findById(materialId)
        .orElseThrow(() -> new IllegalArgumentException("Cannot find material " + materialId));

    material.setCount(material.getCount() + inputMaterial.getCount());

    return inputMaterialRepository.save(inputMaterial);
  }
}
