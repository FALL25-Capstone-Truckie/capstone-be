package capstone_project.repository.entityServices.order;

import capstone_project.entity.order.order.VehicleFuelConsumptionEntity;
import capstone_project.repository.entityServices.common.BaseEntityService;

import java.util.Optional;
import java.util.UUID;

public interface VehicleFuelConsumptionEntityService extends BaseEntityService<VehicleFuelConsumptionEntity, UUID> {

    Optional<VehicleFuelConsumptionEntity> findByVehicleAssignmentId(UUID vehicleAssignmentId);
}
