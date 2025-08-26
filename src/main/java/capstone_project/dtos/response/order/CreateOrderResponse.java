package capstone_project.dtos.response.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CreateOrderResponse(
        BigDecimal totalPrice,
        String notes,
        int totalQuantity,
        BigDecimal totalWeight,
        String orderCode,
        String receiverName,
        String receiverPhone,
        String status,
        String packageDescription,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String senderId,
        String deliveryId,
        String pickupAddressId,
        String categoryId,
        List<GetOrderDetailResponse> orderDetails
) {
}
