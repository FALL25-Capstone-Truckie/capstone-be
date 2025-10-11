package capstone_project.dtos.request.order;

import java.util.UUID;

public record UpdatePackingProofImageRequest(
        UUID id,
        String description
) {
}
