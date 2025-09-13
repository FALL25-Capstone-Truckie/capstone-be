package capstone_project.dtos.response.order;

import capstone_project.entity.auth.UserEntity;
import capstone_project.entity.order.order.OrderEntity;

import java.util.UUID;

public record SignatureResponseDto(
        UUID id,
        String signatureImageUrl,
        String status,
        String notes,
        OrderEntity orderEntity,
        UserEntity user
) {
}
