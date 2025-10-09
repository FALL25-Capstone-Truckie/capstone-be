package capstone_project.dtos.response.dashboard;

public record OnTImeVSLateDeliveriesResponse(
        int onTimeCount,
        int lateCount,
        double onTimePercentage,
        double latePercentage
) {
}
