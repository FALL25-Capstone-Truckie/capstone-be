package capstone_project.repository.entityServices.setting.impl;

import capstone_project.entity.setting.CarrierSettingEntity;
import capstone_project.repository.entityServices.setting.CarrierSettingEntityService;
import capstone_project.repository.repositories.setting.CarrierSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarrierSettingEntityServiceImpl implements CarrierSettingEntityService {

    private final CarrierSettingRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<CarrierSettingEntity> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CarrierSettingEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public CarrierSettingEntity save(CarrierSettingEntity entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
