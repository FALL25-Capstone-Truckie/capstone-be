package capstone_project.dtos.response.order;

import java.math.BigDecimal;

public record CategoryPricingDetailResponse(
        String id,
        BigDecimal priceMultiplier,
        BigDecimal extraFee,

        CategoryResponse categoryResponse
) {
}
