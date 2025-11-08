package capstone_project.dtos.response.refund;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetRefundResponse(
        UUID id,
        BigDecimal refundAmount,
        String bankTransferImage,
        String bankName,
        String accountNumber,
        String accountHolderName,
        String transactionCode,
        LocalDateTime refundDate,
        String notes,
        UUID issueId,
        StaffInfo processedByStaff,
        LocalDateTime createdAt
) {
    public record StaffInfo(
            UUID id,
            String fullName,
            String email
    ) {
    }
}
