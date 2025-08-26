package capstone_project.service.services.order.order;

import capstone_project.common.enums.OrderStatusEnum;
import capstone_project.dtos.request.order.CreateOrderDetailRequest;
import capstone_project.dtos.request.order.CreateOrderRequest;
import capstone_project.dtos.response.order.CreateOrderResponse;
import capstone_project.dtos.response.order.GetOrderResponse;
import capstone_project.entity.order.order.OrderDetailEntity;
import capstone_project.entity.order.order.OrderEntity;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    CreateOrderResponse createOrder(CreateOrderRequest orderRequest, List<CreateOrderDetailRequest> listCreateOrderDetailRequests);

    CreateOrderResponse changeAStatusOrder(UUID orderId, OrderStatusEnum status);

    CreateOrderResponse changeStatusOrderWithAllOrderDetail(UUID orderId, OrderStatusEnum status);

    boolean isValidTransition(OrderStatusEnum current, OrderStatusEnum next);

    List<CreateOrderResponse> getCreateOrderRequestsBySenderId(UUID senderId);

    List<CreateOrderResponse> getCreateOrderRequestsByDeliveryAddressId(UUID deliveryAddressId);

    List<OrderDetailEntity> batchCreateOrderDetails(
            List<CreateOrderDetailRequest> requests, OrderEntity savedOrder);
}
