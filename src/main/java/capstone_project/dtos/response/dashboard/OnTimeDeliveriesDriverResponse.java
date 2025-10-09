package capstone_project.dtos.response.dashboard;

public record OnTimeDeliveriesDriverResponse(
        String id,
        String driverName,
        int onTimeDeliveries,
        int totalDeliveries,
        double onTimePercentage
) {
}
