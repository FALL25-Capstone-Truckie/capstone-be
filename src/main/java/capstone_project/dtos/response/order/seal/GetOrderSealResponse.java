package capstone_project.dtos.response.order.seal;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetOrderSealResponse (
        UUID id,
        String description,
        LocalDateTime sealDate,
        String status,
        UUID sealId,
        UUID vehicleAssignmentId
) {

}
