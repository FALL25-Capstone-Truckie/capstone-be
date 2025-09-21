package capstone_project.repository.entityServices.order.impl;

import capstone_project.entity.order.order.VehicleFuelConsumptionEntity;
import capstone_project.repository.repositories.order.VehicleFuelConsumptionRepository;
import capstone_project.repository.entityServices.order.VehicleFuelConsumptionEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleFuelConsumptionEntityServiceImpl implements VehicleFuelConsumptionEntityService {

    private final VehicleFuelConsumptionRepository repository;

    @Override
    public VehicleFuelConsumptionEntity save(VehicleFuelConsumptionEntity e) {
        return repository.save(e);
    }

    @Override
    public Optional<VehicleFuelConsumptionEntity> findEntityById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<VehicleFuelConsumptionEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<VehicleFuelConsumptionEntity> findByVehicleAssignmentId(UUID vehicleAssignmentId) {
        return repository.findByVehicleAssignmentId(vehicleAssignmentId);
    }
}
