package capstone_project.dtos.response.dashboard;

public record TopSenderResponse(
        String id,
        String senderCompanyName,
        int orderCount,
        int rank
) {
}
