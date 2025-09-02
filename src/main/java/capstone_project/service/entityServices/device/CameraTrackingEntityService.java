package capstone_project.service.entityServices.device;

import capstone_project.entity.device.CameraTrackingEntity;
import capstone_project.service.entityServices.common.BaseEntityService;

import java.util.List;
import java.util.UUID;

public interface CameraTrackingEntityService extends BaseEntityService<CameraTrackingEntity, UUID> {
    List<CameraTrackingEntity> findByVehicleAssignmentEntity_Id(UUID vehicleAssignmentId);
    List<CameraTrackingEntity> findByDeviceEntity_Id(UUID deviceId);
}
