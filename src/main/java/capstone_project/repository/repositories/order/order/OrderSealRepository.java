package capstone_project.repository.repositories.order.order;

import capstone_project.entity.order.order.OrderSealEntity;
import capstone_project.entity.order.order.SealEntity;
import capstone_project.entity.vehicle.VehicleAssignmentEntity;
import capstone_project.repository.repositories.common.BaseRepository;

import java.util.List;

public interface OrderSealRepository extends BaseRepository<OrderSealEntity> {
    List<OrderSealEntity> findBySeal(SealEntity seal);

    OrderSealEntity findByVehicleAssignmentAndStatus(VehicleAssignmentEntity vehicleAssignment, String status);

    // New method to find all order seals for a vehicle assignment
    List<OrderSealEntity> findByVehicleAssignment(VehicleAssignmentEntity vehicleAssignment);
}
