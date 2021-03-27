package com.autopartner.service;

import com.autopartner.domain.InputMaterial;

public interface InputMaterialService {

  Iterable<InputMaterial> getByActiveTrue();

  InputMaterial getById(Long id);

  InputMaterial saveToWarehouse(InputMaterial inputMaterial);

}
