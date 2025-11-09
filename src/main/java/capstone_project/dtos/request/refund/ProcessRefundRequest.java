package capstone_project.dtos.request.refund;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record ProcessRefundRequest(
        @NotNull(message = "Issue ID is required")
        UUID issueId,

        @NotNull(message = "Refund amount is required")
        @Positive(message = "Refund amount must be positive")
        BigDecimal refundAmount,

        @NotBlank(message = "Bank name is required")
        String bankName,

        @NotBlank(message = "Account number is required")
        String accountNumber,

        @NotBlank(message = "Account holder name is required")
        String accountHolderName,

        @NotBlank(message = "Transaction code is required")
        String transactionCode,

        String notes
) {
}
