package capstone_project.repository.repositories.setting;

import capstone_project.entity.setting.CarrierSettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrierSettingRepository extends JpaRepository<CarrierSettingEntity, Long> {
}