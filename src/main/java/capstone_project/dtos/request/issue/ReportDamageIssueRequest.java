package capstone_project.dtos.request.issue;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public record ReportDamageIssueRequest(
        @NotNull UUID vehicleAssignmentId,
        @NotNull UUID issueTypeId,
        @NotNull List<String> orderDetailIds,
        @NotBlank String description,
        Double locationLatitude,
        Double locationLongitude,
        @NotNull List<MultipartFile> damageImages
) {
}
