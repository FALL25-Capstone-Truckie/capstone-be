package capstone_project.dtos.response.pricing;

import java.math.BigDecimal;

public record DistanceRuleResponse(

        String id,
        BigDecimal fromKm,
        BigDecimal toKm
) {
}
