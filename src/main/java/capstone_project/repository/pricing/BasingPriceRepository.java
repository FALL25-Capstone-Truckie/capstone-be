package capstone_project.repository.pricing;

import capstone_project.entity.pricing.BasingPriceEntity;
import capstone_project.repository.common.BaseRepository;

import java.util.Optional;
import java.util.UUID;

public interface BasingPriceRepository extends BaseRepository<BasingPriceEntity> {
    Optional<BasingPriceEntity> findBasingPriceEntityByVehicleRuleEntityIdAndDistanceRuleEntityId(UUID vehicleRuleEntityId, UUID distanceRuleEntityId);
}
