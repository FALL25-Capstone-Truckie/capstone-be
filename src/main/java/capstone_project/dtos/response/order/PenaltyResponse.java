package capstone_project.dtos.response.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response object for penalty information
 */
public record PenaltyResponse(
    UUID id,
    String description,
    BigDecimal amount,
    String status,
    LocalDateTime createdAt,
    UUID vehicleAssignmentId,
    String penaltyType
) {}
