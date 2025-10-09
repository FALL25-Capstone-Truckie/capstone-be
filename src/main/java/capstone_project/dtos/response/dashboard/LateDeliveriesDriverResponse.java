package capstone_project.dtos.response.dashboard;

public record LateDeliveriesDriverResponse(
        String id,
        String driverName,
        int lateDeliveries,
        int totalDeliveries,
        double latePercentage
) {
}
