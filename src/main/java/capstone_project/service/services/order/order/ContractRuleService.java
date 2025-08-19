package capstone_project.service.services.order.order;

import capstone_project.dtos.request.order.ContractRuleRequest;
import capstone_project.dtos.response.order.ContractRuleResponse;

import java.util.List;
import java.util.UUID;

public interface ContractRuleService {
    List<ContractRuleResponse> getContracts();

    ContractRuleResponse getContractById(UUID id);

    ContractRuleResponse createContract(ContractRuleRequest contractRuleRequest);

    ContractRuleResponse updateContract(UUID id, ContractRuleRequest contractRuleRequest);

    void deleteContract(UUID id);


}
