package capstone_project.repository.device;

import capstone_project.entity.device.CameraTrackingEntity;
import capstone_project.repository.common.BaseRepository;

import java.util.List;
import java.util.UUID;

public interface CameraTrackingRepository extends BaseRepository<CameraTrackingEntity> {
    List<CameraTrackingEntity> findByVehicleAssignmentEntity_Id(UUID vehicleAssignmentId);
    List<CameraTrackingEntity> findByDeviceEntity_Id(UUID deviceId);
}
