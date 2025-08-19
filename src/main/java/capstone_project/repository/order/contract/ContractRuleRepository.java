package capstone_project.repository.order.contract;

import capstone_project.entity.order.contract.ContractRuleEntity;
import capstone_project.repository.common.BaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContractRuleRepository extends BaseRepository<ContractRuleEntity> {
    Optional<ContractRuleEntity> findContractRuleEntitiesByContractEntityIdAndVehicleRuleEntityId(UUID contractEntity, UUID vehicleRuleEntity);

    List<ContractRuleEntity> findContractRuleEntitiesByContractEntityId(UUID contractRuleId);
}
