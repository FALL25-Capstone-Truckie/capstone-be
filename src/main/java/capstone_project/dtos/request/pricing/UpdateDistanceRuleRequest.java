package capstone_project.dtos.request.pricing;

import jakarta.validation.constraints.Min;

public record UpdateDistanceRuleRequest(
        @Min(value = 1, message = "fromKm must be greater than 0")
        Integer fromKm,

        @Min(value = 1, message = "fromKm must be greater than 0")
        Integer toKm
) {
}
