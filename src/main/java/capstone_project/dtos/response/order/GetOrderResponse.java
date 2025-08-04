package capstone_project.dtos.response.order;

import java.math.BigDecimal;

public record GetOrderResponse(
    String id,
    BigDecimal totalPrice,
    String notes,
    Integer totalQuantity,
    String orderCode,
    String receiverName,
    String receiverPhone,
    String packageDescription,
    String deliveryAddressId,
    String pickupAddressId,
    String senderId
) {
}
