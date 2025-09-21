package capstone_project.repository.repositories.order;

import capstone_project.entity.order.order.VehicleFuelConsumptionEntity;
import capstone_project.repository.repositories.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface VehicleFuelConsumptionRepository extends BaseRepository<VehicleFuelConsumptionEntity> {

    @Query("SELECT vfc FROM VehicleFuelConsumptionEntity vfc " +
           "LEFT JOIN FETCH vfc.fuelTypeEntity " +
           "WHERE vfc.vehicleAssignmentEntity.id = :vehicleAssignmentId")
    Optional<VehicleFuelConsumptionEntity> findByVehicleAssignmentId(@Param("vehicleAssignmentId") UUID vehicleAssignmentId);
}
