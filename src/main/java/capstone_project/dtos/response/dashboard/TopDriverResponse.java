package capstone_project.dtos.response.dashboard;

public record TopDriverResponse(
        String id,
        String driverName,
        int orderCount,
        int rank
) {
}
