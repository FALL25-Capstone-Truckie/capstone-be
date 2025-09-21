package capstone_project.dtos.response.order;

public record SimpleDriverResponse(
    String id,
    String fullName,
    String phoneNumber
) {}