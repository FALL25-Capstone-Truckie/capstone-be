package capstone_project.dtos.response.issue;

import java.math.BigDecimal;

public record SimpleIssueResponse(
    String id,
    String description,
    BigDecimal locationLatitude,
    BigDecimal locationLongitude,
    String status,
    String vehicleAssignmentId,
    SimpleStaffResponse staff,
    String issueTypeName
) {}