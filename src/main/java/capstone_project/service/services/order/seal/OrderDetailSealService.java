package capstone_project.service.services.order.seal;

import capstone_project.dtos.request.order.seal.OrderDetailSealRequest;
import capstone_project.dtos.response.order.seal.GetOrderDetailSealResponse;
import capstone_project.dtos.response.order.seal.GetSealFullResponse;

public interface OrderDetailSealService {
    GetSealFullResponse assignSealForOrderDetail(OrderDetailSealRequest orderDetailSealRequest);


}
