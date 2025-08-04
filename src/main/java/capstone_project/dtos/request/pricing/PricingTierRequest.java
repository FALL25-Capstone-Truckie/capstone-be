package capstone_project.dtos.request.pricing;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record PricingTierRequest(
        BigDecimal basePrice,
        String description,
        @NotBlank(message = "Pricing tier type is required")
        String pricingTier,

        @NotBlank(message = "pricing rule ID is required")
        String pricingRuleID
) {
}
