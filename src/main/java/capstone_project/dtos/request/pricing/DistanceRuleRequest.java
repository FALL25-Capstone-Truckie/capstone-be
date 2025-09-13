package capstone_project.dtos.request.pricing;

import jakarta.validation.constraints.NotBlank;

public record DistanceRuleRequest(

        @NotBlank(message = "Distance rule tier is required")
        String distanceRuleTier

) {
}
