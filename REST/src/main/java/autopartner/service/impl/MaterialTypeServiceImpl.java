package autopartner.service.impl;

import autopartner.domain.entity.MaterialType;
import autopartner.repository.MaterialTypeRepository;
import autopartner.service.MaterialTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Repository
@Transactional
@Service("MaterialTypeService")
public class MaterialTypeServiceImpl implements MaterialTypeService {

    //private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MaterialTypeRepository materialTypeRepository;

    @Override
    public Iterable<MaterialType> getByActiveTrue() {
        return materialTypeRepository.findByActiveTrue();
    }

    @Override
    public MaterialType getMaterialTypeById(Integer id) {
        return materialTypeRepository.findOne(id);
    }

    @Override
    public MaterialType saveMaterialType(MaterialType materialType) {
        return materialTypeRepository.save(materialType);
    }
}
