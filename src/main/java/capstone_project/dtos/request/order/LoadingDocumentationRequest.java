package capstone_project.dtos.request.order;

import java.util.UUID;
import java.util.List;

public record LoadingDocumentationRequest(
        UUID vehicleAssignmentId,
        String sealCode
) {
}
