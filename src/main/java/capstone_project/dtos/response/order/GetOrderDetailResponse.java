package capstone_project.dtos.response.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GetOrderDetailResponse  (
        BigDecimal weight,
        String description,
        String status,
        LocalDateTime startTime,
        LocalDateTime estimatedStartTime,
        LocalDateTime endTime,
        LocalDateTime estimatedEndTime,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String trackingCode,
        String orderId,
        String orderSizeId,
        String vehicleAssignmentId
)
{
}
