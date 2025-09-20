package capstone_project.service.services.order.seal;

import capstone_project.dtos.request.order.seal.OrderSealRequest;
import capstone_project.dtos.response.order.seal.GetOrderSealResponse;
import capstone_project.dtos.response.order.seal.GetSealFullResponse;

import java.util.UUID;

public interface OrderSealService {
    GetSealFullResponse assignSealForVehicleAssignment(OrderSealRequest orderSealRequest);

    GetSealFullResponse removeSealBySealId(UUID sealId);

    GetSealFullResponse getAllBySealId(UUID sealId);

    GetOrderSealResponse getActiveOrderSealByVehicleAssignmentId(UUID vehicleAssignmentId);
}
