package capstone_project.dtos.response.dashboard;

public record TopOrderCustomerResponse(
        String customerId,
        String companyName,
        Long orderCount

) {
}
