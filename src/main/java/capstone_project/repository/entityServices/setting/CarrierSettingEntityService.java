package capstone_project.repository.entityServices.setting;

import capstone_project.entity.setting.CarrierSettingEntity;
import java.util.List;
import java.util.Optional;

public interface CarrierSettingEntityService {
    List<CarrierSettingEntity> findAll();
    Optional<CarrierSettingEntity> findById(Long id);
    CarrierSettingEntity save(CarrierSettingEntity entity);
    void deleteById(Long id);
    boolean existsById(Long id);
}