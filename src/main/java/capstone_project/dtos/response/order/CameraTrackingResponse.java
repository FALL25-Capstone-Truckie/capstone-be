package capstone_project.dtos.response.order;

import java.time.LocalDateTime;
import java.util.UUID;

public record CameraTrackingResponse (
        UUID id,
        String videoUrl,
        LocalDateTime trackingAt,
        String status,
        UUID vehicleAssignmentId,
        UUID deviceId
){
}
