package capstone_project.repository.entityServices.order.order;

import capstone_project.entity.order.order.OrderSealEntity;
import capstone_project.entity.order.order.SealEntity;
import capstone_project.entity.vehicle.VehicleAssignmentEntity;
import capstone_project.repository.entityServices.common.BaseEntityService;

import java.util.List;
import java.util.UUID;

public interface OrderSealEntityService extends BaseEntityService<OrderSealEntity, UUID> {
    List<OrderSealEntity> saveAll(List<OrderSealEntity> orderSealEntities);

    List<OrderSealEntity> findBySeal(SealEntity seal);

    OrderSealEntity findByVehicleAssignment(VehicleAssignmentEntity vehicleAssignment, String status);
}
