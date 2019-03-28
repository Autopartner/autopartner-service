package autopartner.service.impl;

import autopartner.domain.entity.Material;
import autopartner.repository.MaterialRepository;
import autopartner.service.MaterialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Repository
@Transactional
@Service("materialService")
public class MaterialServiceImpl implements MaterialService {

    //private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public Iterable<Material> getByActiveTrue() {
        return materialRepository.findByActiveTrue();
    }

    @Override
    public Material getMaterialById(Integer id) {
        return materialRepository.findById(id).get();
    }

    @Override
    public Material saveMaterial(Material material) {
        return materialRepository.save(material);
    }
}
