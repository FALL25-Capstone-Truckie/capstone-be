package capstone_project.dtos.response.order;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response object for camera tracking information
 */
public record CameraTrackingResponse(
    UUID id,
    String videoUrl,
    LocalDateTime trackingAt,
    String status,
    UUID vehicleAssignmentId,
    String deviceName
) {}
