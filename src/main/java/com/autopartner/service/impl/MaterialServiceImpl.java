package com.autopartner.service.impl;

import static lombok.AccessLevel.PRIVATE;

import com.autopartner.domain.Material;
import com.autopartner.service.MaterialService;
import com.autopartner.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class MaterialServiceImpl implements MaterialService {

  MaterialRepository materialRepository;

  @Override
  public Iterable<Material> getByActiveTrue() {
    return materialRepository.findByActiveTrue();
  }

  @Override
  public Material getMaterialById(Long id) {
    return materialRepository.findById(id).get();
  }

  @Override
  public Material saveMaterial(Material material) {
    return materialRepository.save(material);
  }
}
