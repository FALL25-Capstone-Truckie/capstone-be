package capstone_project.dtos.response.order;

import java.time.LocalDateTime;
import java.util.UUID;

public record PackingProofImageResponse(
        UUID id,
        String imageUrl,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        UUID vehicleAssignmentId
) {
}
