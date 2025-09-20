package capstone_project.dtos.response.issue;

import java.util.List;

public record SimpleIssueImageResponse(
    SimpleIssueResponse issue,
    List<String> imageUrls
) {}