package capstone_project.dtos.request.order;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CategoryPricingDetailRequest(
        BigDecimal priceMultiplier,
        BigDecimal extraFee,

        @NotBlank(message = "Category ID cannot be blank")
        String categoryId
) {
}
