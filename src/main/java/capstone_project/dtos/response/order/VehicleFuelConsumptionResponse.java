package capstone_project.dtos.response.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response object for vehicle fuel consumption information
 */
public record VehicleFuelConsumptionResponse(
    UUID id,
    BigDecimal odometerReadingAtRefuel,
    String odometerAtStartUrl,
    String odometerAtFinishUrl,
    String odometerAtEndUrl,
    LocalDateTime dateRecorded,
    String notes,
    String fuelTypeName,
    String fuelTypeDescription
) {}
