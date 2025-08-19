package capstone_project.service.services.order.order.impl;

import capstone_project.common.enums.CommonStatusEnum;
import capstone_project.common.enums.ErrorEnum;
import capstone_project.common.exceptions.dto.NotFoundException;
import capstone_project.dtos.request.order.ContractRuleRequest;
import capstone_project.dtos.response.order.ContractRuleResponse;
import capstone_project.entity.order.contract.ContractRuleEntity;
import capstone_project.service.entityServices.order.contract.ContractRuleEntityService;
import capstone_project.service.mapper.order.ContractRuleMapper;
import capstone_project.service.services.order.order.ContractRuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContractRuleServiceImpl implements ContractRuleService {

    private final ContractRuleEntityService contractRuleEntityService;
    private final ContractRuleMapper contractRuleMapper;

    @Override
    public List<ContractRuleResponse> getContracts() {
        log.info("Fetching all contract rules");
        List<ContractRuleEntity> contractRule = contractRuleEntityService.findAll();

        if (contractRule.isEmpty()) {
            log.warn("No contract rules found");
            throw new NotFoundException(
                    ErrorEnum.NOT_FOUND.getMessage(),
                    ErrorEnum.NOT_FOUND.getErrorCode()
            );
        }

        return contractRule.stream()
                .map(contractRuleMapper::toContractRuleResponse)
                .toList();
    }

    @Override
    public ContractRuleResponse getContractById(UUID id) {
        log.info("Fetching contract rule with ID: {}", id);

        ContractRuleEntity contractRule = contractRuleEntityService.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        ErrorEnum.NOT_FOUND.getMessage(),
                        ErrorEnum.NOT_FOUND.getErrorCode()
                ));

        return contractRuleMapper.toContractRuleResponse(contractRule);
    }

    @Override
    public ContractRuleResponse createContract(ContractRuleRequest contractRuleRequest) {
        log.info("Creating new contract rule with request: {}", contractRuleRequest);

        if (contractRuleRequest.vehicleRuleId() == null || contractRuleRequest.contractEntityId().isEmpty()) {
            log.error("Vehicle rule ID & Contract ID are required");
            throw new NotFoundException(
                    "Vehicle rule ID and Contract ID are required",
                    ErrorEnum.NOT_FOUND.getErrorCode()
            );
        }


        if (contractRuleRequest.numOfVehicles() == null || contractRuleRequest.numOfVehicles() <= 0) {
            log.error("Number of vehicles must be greater than zero");
            throw new NotFoundException(
                    "Number of vehicles must be greater than zero",
                    ErrorEnum.INVALID.getErrorCode()
            );
        }

        UUID contractEntityUUId = UUID.fromString(contractRuleRequest.contractEntityId());
        UUID vehicleRuleUUId = UUID.fromString(contractRuleRequest.vehicleRuleId());

        if (contractRuleEntityService.findContractRuleEntitiesByContractEntityIdAndVehicleRuleEntityId(contractEntityUUId, vehicleRuleUUId).isPresent()) {
            log.error("Contract rule with vehicle rule ID {} and contract ID {} already exists", vehicleRuleUUId, contractEntityUUId);
            throw new NotFoundException(
                    "Contract rule with this vehicle rule ID and contract ID already exists",
                    ErrorEnum.ALREADY_EXISTED.getErrorCode()
            );
        }

        ContractRuleEntity contractRuleEntity = contractRuleMapper.mapRequestToEntity(contractRuleRequest);

        contractRuleEntity.setStatus(CommonStatusEnum.ACTIVE.name());

        ContractRuleEntity savedContractRule = contractRuleEntityService.save(contractRuleEntity);

        return contractRuleMapper.toContractRuleResponse(savedContractRule);
    }

    @Override
    public ContractRuleResponse updateContract(UUID id, ContractRuleRequest contractRuleRequest) {
        log.info("Updating contract rule with ID: {}", id);

        ContractRuleEntity existingContractRule = contractRuleEntityService.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        ErrorEnum.NOT_FOUND.getMessage(),
                        ErrorEnum.NOT_FOUND.getErrorCode()
                ));

        UUID contractEntityUUId = UUID.fromString(contractRuleRequest.contractEntityId());
        UUID vehicleRuleUUId = UUID.fromString(contractRuleRequest.vehicleRuleId());

        Optional<ContractRuleEntity> existing = contractRuleEntityService
                .findContractRuleEntitiesByContractEntityIdAndVehicleRuleEntityId(contractEntityUUId, vehicleRuleUUId);

        if (existing.isPresent() && !existing.get().getId().equals(id)) {
            log.error("Contract rule with vehicle rule ID {} and contract ID {} already exists", vehicleRuleUUId, contractEntityUUId);
            throw new NotFoundException(
                    "Contract rule with this vehicle rule ID and contract ID already exists",
                    ErrorEnum.ALREADY_EXISTED.getErrorCode()
            );
        }

        contractRuleMapper.toContractRuleEntity(contractRuleRequest, existingContractRule);

        ContractRuleEntity savedContractRule = contractRuleEntityService.save(existingContractRule);

        return contractRuleMapper.toContractRuleResponse(savedContractRule);
    }

    @Override
    public void deleteContract(UUID id) {

    }
}
