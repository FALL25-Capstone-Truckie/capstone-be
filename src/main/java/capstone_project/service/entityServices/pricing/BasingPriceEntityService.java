package capstone_project.service.entityServices.pricing;

import capstone_project.entity.pricing.BasingPriceEntity;
import capstone_project.service.entityServices.common.BaseEntityService;

import java.util.Optional;
import java.util.UUID;

public interface BasingPriceEntityService extends BaseEntityService<BasingPriceEntity, UUID> {
    Optional<BasingPriceEntity> findBasingPriceEntityByVehicleRuleEntityIdAndDistanceRuleEntityId(UUID vehicleRuleEntityId, UUID distanceRuleEntityId);
}
