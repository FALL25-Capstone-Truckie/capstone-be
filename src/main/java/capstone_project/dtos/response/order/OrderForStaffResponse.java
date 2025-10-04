package capstone_project.dtos.response.order;

import capstone_project.dtos.response.order.contract.SimpleContractResponse;
import capstone_project.dtos.response.order.transaction.SimpleTransactionResponse;
import java.util.List;

/**
 * Main response object for staff order with all enhanced information
 */
public record OrderForStaffResponse(
    StaffOrderResponse order,
    SimpleContractResponse contract,
    List<SimpleTransactionResponse> transactions
) {}
