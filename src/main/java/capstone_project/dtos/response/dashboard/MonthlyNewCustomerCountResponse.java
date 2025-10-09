package capstone_project.dtos.response.dashboard;

public record MonthlyNewCustomerCountResponse(
        int month,
        long count
) {
}
