package com.autopartner.service;

import com.autopartner.domain.Material;

public interface MaterialService {

  Iterable<Material> getByActiveTrue();

  Material getMaterialById(Long id);

  Material saveMaterial(Material material);

}
