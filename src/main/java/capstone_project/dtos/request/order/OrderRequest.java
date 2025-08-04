package capstone_project.dtos.request.order;

import java.math.BigDecimal;

public record OrderRequest(
        BigDecimal totalPrice,
        String notes,
        Integer totalQuantity,
        BigDecimal totalWeight,
        String orderCode,
        String receiverName,
        String receiverPhone,
        String packageDescription,
        String deliveryAddressId,
        String pickupAddressId,
        String senderId

) {
}
