package capstone_project.dtos.response.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Enhanced vehicle response with full information for staff
 */
public record StaffVehicleResponse(
    UUID id,
    String manufacturer,
    String model,
    String licensePlateNumber,
    String vehicleType
) {}
