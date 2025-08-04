package capstone_project.service.services.order.order;

import capstone_project.dtos.request.order.ContractRequest;
import capstone_project.dtos.response.order.ContractResponse;

import java.util.List;
import java.util.UUID;

public interface ContractService {
    List<ContractResponse> getAllContracts();

    ContractResponse getContractById(UUID id);

    ContractResponse createContract(ContractRequest contractRequest);

    ContractResponse updateContract(UUID id, ContractRequest contractRequest);

    void deleteContract(UUID id);
}
