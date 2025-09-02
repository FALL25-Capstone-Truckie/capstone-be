package capstone_project.service.services.order.order;

import capstone_project.dtos.request.order.CameraTrackingRequest;
import capstone_project.dtos.response.order.CameraTrackingResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface CameraTrackingService {
    CameraTrackingResponse saveTracking(CameraTrackingRequest request) throws IOException;

    List<CameraTrackingResponse> getAllTracking() throws IOException;

    List<CameraTrackingResponse> getByVehicleAssignment(UUID vehicleAssignmentId) throws IOException;

    List<CameraTrackingResponse> getByDevice(UUID deviceId) throws IOException;

}
