package capstone_project.repository.repositories.device;

import capstone_project.entity.device.CameraTrackingEntity;
import capstone_project.repository.repositories.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CameraTrackingRepository extends BaseRepository<CameraTrackingEntity> {

    @Query("SELECT ct FROM CameraTrackingEntity ct " +
           "LEFT JOIN FETCH ct.deviceEntity " +
           "WHERE ct.vehicleAssignmentEntity.id = :vehicleAssignmentId")
    List<CameraTrackingEntity> findByVehicleAssignmentId(@Param("vehicleAssignmentId") UUID vehicleAssignmentId);
}
