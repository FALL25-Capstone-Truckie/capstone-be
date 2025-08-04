package capstone_project.dtos.response.pricing;

public record GetBasingPriceResponse(
        String id,
        String basePrice,

        VehicleRuleResponse vehicleRuleResponse,
        DistanceRuleResponse distanceRuleResponse
) {
}
