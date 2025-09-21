package capstone_project.dtos.response.order;

import capstone_project.dtos.response.order.contract.SimpleContractResponse;
import capstone_project.dtos.response.order.transaction.SimpleTransactionResponse;

import java.util.List;

public record SimpleOrderForCustomerResponse(
    SimpleOrderResponse order,
    SimpleContractResponse contract,
    List<SimpleTransactionResponse> transactions
) {}