package capstone_project.dtos.request.order;

import java.util.UUID;

public record CameraTrackingRequest(
        byte[] file,
        String fileName,
        String folder,
        String status,
        UUID vehicleAssignmentId,
        UUID deviceId
) {
}
