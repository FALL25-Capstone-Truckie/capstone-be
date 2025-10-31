package capstone_project.dtos.request.order;

import java.util.UUID;

public record CreatePackingProofImageRequest(
        UUID vehicleAssignmentId,
        String description
) {
}
