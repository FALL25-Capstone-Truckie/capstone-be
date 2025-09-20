package capstone_project.dtos.response.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response object for penalty history information
 */
public record PenaltyHistoryResponse(
    UUID id,
    String violationType,
    String violationDescription,
    BigDecimal penaltyAmount,
    LocalDate penaltyDate,
    String location,
    String status,
    LocalDateTime paymentDate,
    String disputeReason,
    StaffDriverResponse driver
) {}
